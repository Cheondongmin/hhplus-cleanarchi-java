package com.hhplus_cleanarchi_java.domain.lecture.repository;

import com.hhplus_cleanarchi_java.domain.lecture.LectureInfo;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;

import java.util.List;

public interface LectureRepository {
    Lecture save(Lecture lecture);
    List<LectureInfo> findAllLectureInfo();
    Lecture findById(Long id);
}
