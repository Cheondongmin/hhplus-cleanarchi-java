package com.hhplus_cleanarchi_java.infrastructure.lecture.repository;

import com.hhplus_cleanarchi_java.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.infrastructure.lecture.persistence.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;

    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public List<LectureInfo> findAllLectureInfo() {
        return lectureJpaRepository.findAllLectureInfo();
    }

    @Override
    public Lecture findById(Long id) {
        return lectureJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하는 강의가 없습니다"));
    }
}
