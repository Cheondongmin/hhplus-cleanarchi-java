package com.hhplus_cleanarchi_java.interfaces.dto.res;

import com.hhplus_cleanarchi_java.domain.lecture.dto.LectureInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record LectureInfoApplicationRes(
        Long lectureId,
        String lectureName,
        String teacherName,
        Long lectureScheduleId,
        int limitedCount,
        int registeredCount,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
    public static List<LectureInfoApplicationRes> from(List<LectureInfo> lectureInfos) {
        List<LectureInfoApplicationRes> result = new ArrayList<>();
        for (LectureInfo lectureInfo : lectureInfos) {
            result.add(from(lectureInfo));
        }
        return result;
    }

    public static LectureInfoApplicationRes from(LectureInfo lectureInfo) {
        return new LectureInfoApplicationRes(
                lectureInfo.getLectureId(),
                lectureInfo.getName(),
                lectureInfo.getTeacher(),
                lectureInfo.getLectureScheduleId(),
                lectureInfo.getLimitedCount(),
                lectureInfo.getRegisteredCount(),
                lectureInfo.getStartDateTime(),
                lectureInfo.getEndDateTime()
        );
    }
}
