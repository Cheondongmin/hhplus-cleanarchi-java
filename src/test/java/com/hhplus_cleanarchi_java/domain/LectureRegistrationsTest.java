package com.hhplus_cleanarchi_java.domain;

import com.hhplus_cleanarchi_java.IntegrationTest;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureScheduleRepository;
import com.hhplus_cleanarchi_java.domain.lecture.service.LectureRegistrations;
import com.hhplus_cleanarchi_java.fixture.LectureRegistrationFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.hhplus_cleanarchi_java.fixture.LectureFixture.자바_특강;
import static com.hhplus_cleanarchi_java.fixture.LectureScheduleFixture.특강_스케줄;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LectureRegistrationsTest extends IntegrationTest {

    @Autowired
    private LectureRegistrations lectureRegistrations;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private LectureRegistrationRepository lectureRegistrationRepository;

    private final long userId = 1L;

    @Test
    void 사용자는_특강을_신청할_수_있다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.now().plusSeconds(5L);
        LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(60L);
        LectureSchedule lectureSchedule = 자바_특강_등록(0, startDateTime, endDateTime);

        // when
        LectureRegistration lectureRegistration = lectureRegistrations.register(lectureSchedule.getId(), userId);

        // then
        assertEquals(lectureRegistration.getLectureScheduleId(), lectureSchedule.getId());
        assertEquals(lectureRegistration.getUserId(), userId);
    }

    @Test
    void 특강에_이미_신청한_사용자는_같은_특강을_중복해서_신청할_수_없다() {
        // given
        // 특강 등록 시간과 종료 시간을 설정하고, 특강 스케줄을 생성합니다.
        LocalDateTime startDateTime = LocalDateTime.now().plusSeconds(5L); // 특강 시작 시간은 현재 시간보다 5초 뒤
        LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(60L); // 특강 종료 시간은 현재 시간보다 60초 뒤
        LectureSchedule lectureSchedule = 자바_특강_등록(0, startDateTime, endDateTime); // 자바 특강 스케줄 등록 (등록 인원 0명)

        // 특강 신청을 미리 한 사용자의 정보를 저장합니다.
        lectureRegistrationRepository.save(LectureRegistrationFixture.자바_특강_신청(lectureSchedule.getId(), userId));
        // 사용자는 해당 특강에 이미 신청한 상태입니다.

        // when & then: 다시 같은 특강을 신청하려고 하면 예외가 발생해야 합니다.
        assertThatThrownBy(() -> lectureRegistrations.register(lectureSchedule.getId(), userId)) // 같은 특강 신청 시도
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 신청한 특강입니다.");
    }

    @Test
    void 특강_시작일자가_지난경우_특강을_신청할_수_없다() {
        // given: 특강의 시작 시간이 이미 지난 상태로 설정합니다.
        LocalDateTime startDateTime = LocalDateTime.now().minusSeconds(1L); // 특강 시작 시간은 현재 시간보다 1초 전에 시작
        LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(60L); // 특강 종료 시간은 현재 시간보다 60초 뒤
        LectureSchedule lectureSchedule = 자바_특강_등록(0, startDateTime, endDateTime); // 자바 특강 스케줄 등록 (등록 인원 0명)

        // when & then 특강이 이미 시작된 상태에서 신청을 시도하면 예외가 발생해야 합니다.
        assertThatThrownBy(() -> lectureRegistrations.register(lectureSchedule.getId(), userId)) // 특강 신청 시도
                .isInstanceOf(RuntimeException.class)
                .hasMessage("특강 시작일자가 이미 지났습니다.");
    }

    @Test
    void 특강_제한인원인_30명안에_들지_못했을_경우_특강을_신청할_수_없다() {
        // given: 특강의 시작 시간이 아직 오지 않았고, 제한 인원은 30명으로 설정합니다.
        LocalDateTime startDateTime = LocalDateTime.now().plusSeconds(5L); // 특강 시작 시간은 5초 후
        LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(60L); // 특강 종료 시간은 60초 후
        LectureSchedule lectureSchedule = 자바_특강_등록(30, startDateTime, endDateTime); // 등록 된 인원이 30명인 자바 특강 스케줄 등록 (제한 인원은 30명으로 고정)

        // when & then 제한 인원이 모두 찬 상태에서 신청을 시도하면 예외가 발생해야 합니다.
        assertThatThrownBy(() -> lectureRegistrations.register(lectureSchedule.getId(), userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("신청 가능한 인원을 초과하였습니다.");
    }

    // 특강 등록 메소드
    private LectureSchedule 자바_특강_등록(int registeredCount, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Lecture lecture = lectureRepository.save(자바_특강());
        return lectureScheduleRepository.save(특강_스케줄(
                lecture.getId(),
                registeredCount,
                startDateTime,
                endDateTime
        ));
    }

}
