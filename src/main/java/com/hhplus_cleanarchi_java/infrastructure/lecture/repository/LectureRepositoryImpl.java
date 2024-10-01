package com.hhplus_cleanarchi_java.infrastructure.lecture.repository;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.LectureInfo;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.infrastructure.lecture.persistence.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;

    public Lecture save(Lecture lecture){
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public List<LectureInfo> findAllLectureInfo() {
        return lectureJpaRepository.findAllLectureInfo();
    }

}
