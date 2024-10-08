package com.hhplus_cleanarchi_java.app.domain.lecture.repository;

import com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.Lecture;

import java.util.List;

public interface LectureRepository {
    Lecture save(Lecture lecture);

    List<LectureInfo> findAllLectureInfo();

    Lecture findById(Long id);
}
