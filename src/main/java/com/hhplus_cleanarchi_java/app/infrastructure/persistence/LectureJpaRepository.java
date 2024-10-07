package com.hhplus_cleanarchi_java.app.infrastructure.persistence;

import com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    @Query("""
                SELECT new com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureInfo(
                    ls.id,
                    l.id,
                    l.name,
                    l.teacher,
                    ls.limitedCount,
                    ls.registeredCount,
                    ls.startDateTime,
                    ls.endDateTime
                )
                 FROM Lecture l
                 JOIN LectureSchedule ls ON l.id = ls.lectureId
                 WHERE ls.startDateTime >= CURRENT_TIMESTAMP
                 AND ls.registeredCount < 30
                order by l.id asc, ls.startDateTime asc
            """)
    List<LectureInfo> findAllLectureInfo();
}
