package com.hhplus_cleanarchi_java.app.domain.lecture.repository;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;

import java.util.List;

public interface LectureRegistrationRepository {
    LectureRegistration save(LectureRegistration lectureRegistration);

    List<LectureRegistration> findBy(Long lectureScheduleId, Long userId);

    List<LectureRegistration> findByUserId(Long userId);

    long count();
}
