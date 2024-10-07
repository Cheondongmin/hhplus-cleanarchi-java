package com.hhplus_cleanarchi_java.app.domain.lecture.repository;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;

public interface LectureScheduleRepository {
    LectureSchedule findById(Long id);

    LectureSchedule save(LectureSchedule lectureSchedule);
}
