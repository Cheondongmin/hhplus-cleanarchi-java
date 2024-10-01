package com.hhplus_cleanarchi_java.infrastructure.lecture.persistence;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureSchedule, Long> {
    Optional<LectureSchedule> findByLectureId(Long lectureId);
}
