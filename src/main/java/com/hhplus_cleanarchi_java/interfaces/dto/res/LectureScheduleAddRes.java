package com.hhplus_cleanarchi_java.interfaces.dto.res;

public record LectureScheduleAddRes(
        long lectureScheduleId,
        String startDateTime,
        String endDateTime
) {
}
