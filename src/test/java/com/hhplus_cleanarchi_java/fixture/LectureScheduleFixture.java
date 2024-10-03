package com.hhplus_cleanarchi_java.fixture;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;

import java.time.LocalDateTime;

public class LectureScheduleFixture {
    public static LectureSchedule 특강_스케줄(Long lectureId, int registeredCount, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new LectureSchedule(lectureId, 30, registeredCount, startDateTime, endDateTime);
    }
}
