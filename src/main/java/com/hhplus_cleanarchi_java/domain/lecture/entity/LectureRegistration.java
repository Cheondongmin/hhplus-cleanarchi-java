package com.hhplus_cleanarchi_java.domain.lecture.entity;

import com.hhplus_cleanarchi_java.domain.enums.IsDelete;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LectureRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_schedule_id", nullable = false)
    private Long lectureScheduleId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "insert_dt", nullable = false, updatable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsDelete isDelete;

    public LectureRegistration(Long lectureScheduleId, Long userId) {
        this.lectureScheduleId = lectureScheduleId;
        this.userId = userId;
        this.insertDt = LocalDateTime.now();
        this.updateDt = LocalDateTime.now();
        this.isDelete = IsDelete.N;
    }
}
