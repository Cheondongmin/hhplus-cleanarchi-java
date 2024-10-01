package com.hhplus_cleanarchi_java.infrastructure.lecture.persistence;

import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.LectureInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    @Query("""
                SELECT new com.hhplus_cleanarchi_java.domain.lecture.LectureInfo(
                    ls.id,
                    l.id,
                    l.name,
                    ls.limitedCount,
                    ls.registeredCount,
                    ls.startDateTime,
                    ls.endDateTime
                )
                 FROM Lecture l
                 JOIN LectureSchedule ls ON l.id = ls.lectureId
                order by l.id asc, ls.startDateTime asc
            """)
    List<LectureInfo> findAllLectureInfo();
}
