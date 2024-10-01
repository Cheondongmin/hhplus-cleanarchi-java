package com.hhplus_cleanarchi_java.infrastructure.lecture.repository;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.infrastructure.lecture.persistence.LectureRegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {
    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;

    public LectureRegistration save(LectureRegistration lectureRegistration) {
        return lectureRegistrationJpaRepository.save(lectureRegistration);
    }

    @Override
    public List<LectureRegistration> findBy(Long lectureScheduleId, Long userId) {
        return lectureRegistrationJpaRepository.findByLectureScheduleIdAndUserId(lectureScheduleId, userId);
    }

    @Override
    public List<LectureRegistration> findByUserId(Long userId) {
        return lectureRegistrationJpaRepository.findByUserId(userId);
    }

    @Override
    public long count() {
        return lectureRegistrationJpaRepository.count();
    }
}
