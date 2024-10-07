package com.hhplus_cleanarchi_java.app.domain.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureRegisterInfo {
    public Long registrationId;
    private String lectureName;
    private String teacherName;
    private String startTime;
    private String endTime;
}
