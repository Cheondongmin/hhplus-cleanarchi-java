package com.hhplus_cleanarchi_java.application;

import com.hhplus_cleanarchi_java.IntegrationTest;
import com.hhplus_cleanarchi_java.application.lecture.LectureFacade;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hhplus_cleanarchi_java.fixture.LectureFixture.자바_특강;
import static com.hhplus_cleanarchi_java.fixture.LectureScheduleFixture.특강_스케줄;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class OptimisticLectureFacadeTest extends IntegrationTest {

    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private LectureRegistrationRepository lectureRegistrationRepository;

    @Test
    void 낙관적_락을_이용한_특강_신청_인원_제한_동시성_테스트() throws Exception {
        // given: 특강과 특강 스케줄을 설정
        Lecture lecture = lectureRepository.save(
                자바_특강() // 자바 특강 생성 및 저장
        );

        LocalDateTime now = LocalDateTime.now();
        lectureScheduleRepository.save(특강_스케줄(  // 특강 스케줄 생성 및 저장 (시작 시간과 종료 시간 설정)
                lecture.getId(),
                0, // 초기 등록 인원 0명
                now.plusHours(1L), // 1시간 뒤 시작
                now.plusHours(2L) // 2시간 뒤 종료
        ));

        int userCount = 40; // 동시의 40명의 사용자
        int lectureLimitCount = 30; // 특강의 최대 신청 가능 인원 30명
        ExecutorService executorService = Executors.newFixedThreadPool(userCount); // 스레드 풀 생성 (동시 실행을 위한)
        CountDownLatch countDownLatch = new CountDownLatch(userCount); // 모든 작업이 완료될 때까지 기다리기 위한 CountDownLatch

        AtomicInteger successCount = new AtomicInteger(); // 성공적으로 신청한 사용자 수를 추적
        AtomicInteger failCount = new AtomicInteger(); // 실패한 사용자 수를 추적

        // when: 40명의 사용자가 동시에 특강 신청을 시도
        for (int i = 1; i <= userCount; i++) {
            final long userId = i; // 사용자 ID를 루프 인덱스 i로 설정하여 각 사용자가 고유한 ID를 가짐
            executorService.execute(() -> { // 각 사용자에 대해 스레드 실행
                try {
                    lectureFacade.apply(lecture.getId(), userId); // 고유한 userId로 특강 신청 시도
                    successCount.incrementAndGet(); // 성공 시 성공 카운트 증가
                } catch (Exception e) {
                    System.out.println(e.getMessage()); // 에러 메시지 출력
                    failCount.incrementAndGet(); // 실패 카운트 증가
                } finally {
                    countDownLatch.countDown();  // 작업 완료 후 CountDownLatch 카운트 감소
                }
            });
        }

        countDownLatch.await(); // 모든 스레드가 종료될 때까지 대기

        // then: 등록된 신청 수가 최대 인원과 일치하는지 검증
        long registeredCount = lectureRegistrationRepository.count(); // 등록된 신청 인원 수 확인
        assertThat(registeredCount).isEqualTo(lectureLimitCount); // 등록된 인원이 30명인지 확인
    }

    @Test
    void 동일_유저가_같은_특강을_5번_신청했을_때_1번만_성공하는지_검증() throws Exception {
        // given
        Lecture lecture = lectureRepository.save(자바_특강());
        LocalDateTime startDateTime = LocalDateTime.now().plusSeconds(5L);
        LocalDateTime endDateTime = LocalDateTime.now().plusMinutes(1L);
        LectureSchedule lectureSchedule = lectureScheduleRepository.save(특강_스케줄(lecture.getId(), 0, startDateTime, endDateTime));

        long userId = 1L;
        int requestCount = 5; // 동일한 유저가 5번 신청

        ExecutorService executorService = Executors.newFixedThreadPool(requestCount);
        CountDownLatch latch = new CountDownLatch(requestCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // when
        for (int i = 0; i < requestCount; i++) {
            executorService.execute(() -> {
                try {
                    lectureFacade.apply(lectureSchedule.getId(), userId);
                    successCount.incrementAndGet(); // 성공 시 카운트 증가
                } catch (Exception e) {
                    failCount.incrementAndGet(); // 실패 시 카운트 증가
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 대기

        // then
        long registeredCount = lectureRegistrationRepository.count();

        // 성공 횟수는 1번, 실패 횟수는 4번이어야 한다
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(4);
        assertThat(registeredCount).isEqualTo(1); // 실제로 등록된 신청 수는 1개여야 함
    }
}