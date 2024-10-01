package com.hhplus_cleanarchi_java.domain;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LectureRegistrationTest {

    @Test
    void lectureId와_userId가_같은지_비교() {
        // given 이미 존재하는 특강 예약이 있는지 체크 하는 로직
        long userId = 1L;
        long lectureId = 1L;

        LectureRegistration lectureRegistration = new LectureRegistration(lectureId, userId);
        assertEquals(lectureRegistration.getLectureScheduleId(), lectureId);
        assertEquals(lectureRegistration.getUserId(), userId);
    }

}
