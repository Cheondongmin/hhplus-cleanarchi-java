package com.hhplus_cleanarchi_java.interfaces.dto.res;

import com.hhplus_cleanarchi_java.domain.lecture.dto.LectureRegisterInfo;

import java.util.ArrayList;
import java.util.List;

public record LectureRegisterResultRes(
        Long registrationId,
        String lectureName,
        String teacherName,
        String startTime,
        String endTime
) {
    public static List<LectureRegisterResultRes> from(List<LectureRegisterInfo> lectureRegisterInfoList) {
        List<LectureRegisterResultRes> result = new ArrayList<>();
        for (LectureRegisterInfo lectureRegistration : lectureRegisterInfoList) {
            result.add(LectureRegisterResultRes.from(lectureRegistration));
        }
        return result;
    }

    public static LectureRegisterResultRes from(LectureRegisterInfo lectureRegisterInfo) {
        return new LectureRegisterResultRes(
                lectureRegisterInfo.getRegistrationId(),
                lectureRegisterInfo.getLectureName(),
                lectureRegisterInfo.getTeacherName(),
                lectureRegisterInfo.getStartTime(),
                lectureRegisterInfo.getEndTime()
        );
    }
}
