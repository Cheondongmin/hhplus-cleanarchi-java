package com.hhplus_cleanarchi_java.domain.lecture.repository;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;

import java.util.List;

public interface LectureRegistrationRepository {
    LectureRegistration save(LectureRegistration lectureRegistration);
    List<LectureRegistration> findBy(Long lectureScheduleId, Long memberId);
    long count();
}
