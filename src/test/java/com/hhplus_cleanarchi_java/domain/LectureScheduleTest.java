package com.hhplus_cleanarchi_java.domain;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.hhplus_cleanarchi_java.fixture.LectureScheduleFixture.특강_스케줄;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// LectureSchedule 엔티티의 로직의 메소드 단위 테스트
public class LectureScheduleTest {

    @Test
    void 특강_시작시간이_지난경우_true_반환() {
        // given: 현재 시간보다 하루 전 시작된 특강 스케줄 생성
        LectureSchedule lectureSchedule = 특강_스케줄(1L, 10, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusSeconds(1L));

        // when & then: 특강 시작 시간이 지났는지 확인
        assertThat(lectureSchedule.isOverStartDateTime()).isTrue();
    }

    @Test
    void 특강_시작시간이_지나지_않은_경우_false_반환() {
        // given: 현재 시간 이후에 시작되는 특강 스케줄 생성
        LectureSchedule lectureSchedule = 특강_스케줄(1L, 10, LocalDateTime.now().plusSeconds(1L), LocalDateTime.now().plusDays(1).plusHours(2));

        // when & then: 특강 시작 시간이 지나지 않았는지 확인
        assertThat(lectureSchedule.isOverStartDateTime()).isFalse();
    }

    @Test
    void 특강_제한인원이_모두_찬_경우_예외_발생() {
        // given: 제한 인원이 모두 찬 상태의 특강 스케줄 생성
        LectureSchedule lectureSchedule = 특강_스케줄(1L, 30, LocalDateTime.now().minusSeconds(1L), LocalDateTime.now().plusDays(1).plusHours(2));

        // when: 등록 인원을 증가시키려 할 때 예외 발생 확인
        Exception exception = assertThrows(IllegalArgumentException.class, lectureSchedule::increaseRegisterCount);

        // then: 예외 메시지가 올바른지 검증
        assertEquals("신청 가능한 인원을 초과하였습니다.", exception.getMessage());
    }

}
