package com.hhplus_cleanarchi_java.fixture;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;

public class LectureRegistrationFixture {

    public static LectureRegistration 자바_특강_신청(long lectureId, long userId) {
        return new LectureRegistration(lectureId, userId);
    }

}
