package com.hhplus_cleanarchi_java.application;

import com.hhplus_cleanarchi_java.IntegrationTest;
import com.hhplus_cleanarchi_java.application.lecture.LectureFacade;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.hhplus_cleanarchi_java.fixture.LectureFixture.자바_특강;
import static com.hhplus_cleanarchi_java.fixture.LectureScheduleFixture.특강_스케줄;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class LectureFacadeTest extends IntegrationTest {

    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private LectureRegistrationRepository lectureRegistrationRepository;

    @Test
    void 특강_신청에_성공한다() {
        // given: 특강과 특강 스케줄을 준비 (특강 시작 시간과 종료 시간 설정)
        LocalDateTime startDateTime = LocalDateTime.now().plusSeconds(5L);
        LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(60L);
        Lecture lecture = lectureRepository.save(자바_특강()); // 자바 특강 생성 및 저장
        LectureSchedule lectureSchedule = 특강_및_스케줄_등록(lecture, 0, startDateTime, endDateTime); // 특강 스케줄 생성 및 저장
        Long lectureScheduleId = lectureSchedule.getId(); // 스케줄 ID 추출

        long userId = 1L; // 사용자 ID

        // when: 해당 스케줄에 사용자가 특강 신청을 진행
        lectureFacade.apply(lectureScheduleId, userId);

        // then 특강 신청이 잘 저장되었는지 검증 (특강 신청이 1건 생성되었는지 확인)
        List<LectureRegistration> lectureRegistrations = lectureRegistrationRepository.findBy(lectureScheduleId, userId);
        assertThat(lectureRegistrations).hasSize(1) // 신청된 건수가 1건인지 확인
                .extracting("userId", "lectureScheduleId") // 신청된 특강의 사용자 ID와 스케줄 ID 추출
                .containsExactly(
                        tuple(userId, lectureScheduleId) // 기대 값과 비교
                );
    }


    private LectureSchedule 특강_및_스케줄_등록(Lecture lecture, int registeredCount, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        return lectureScheduleRepository.save(특강_스케줄(
                lecture.getId(),
                registeredCount,
                startDateTime,
                endDateTime
        ));
    }

}
