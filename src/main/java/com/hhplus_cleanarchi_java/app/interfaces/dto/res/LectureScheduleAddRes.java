package com.hhplus_cleanarchi_java.app.interfaces.dto.res;

public record LectureScheduleAddRes(
        long lectureScheduleId,
        String startDateTime,
        String endDateTime
) {
}
