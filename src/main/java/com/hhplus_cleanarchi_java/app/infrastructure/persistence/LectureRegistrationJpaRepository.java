package com.hhplus_cleanarchi_java.app.infrastructure.persistence;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistration, Long> {
    List<LectureRegistration> findByLectureScheduleIdAndUserId(Long lectureScheduleId, Long userId);

    List<LectureRegistration> findByUserId(Long userId);
}
