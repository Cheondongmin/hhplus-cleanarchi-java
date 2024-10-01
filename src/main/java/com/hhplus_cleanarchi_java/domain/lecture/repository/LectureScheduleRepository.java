package com.hhplus_cleanarchi_java.domain.lecture.repository;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;

public interface LectureScheduleRepository {
    LectureSchedule findById(Long id);

    LectureSchedule save(LectureSchedule lectureSchedule);
}
