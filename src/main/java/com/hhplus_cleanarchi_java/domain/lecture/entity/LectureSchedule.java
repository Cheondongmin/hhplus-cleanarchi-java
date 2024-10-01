package com.hhplus_cleanarchi_java.domain.lecture.entity;

import com.hhplus_cleanarchi_java.domain.enums.IsDelete;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class LectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "limited_count", nullable = false)
    private int limitedCount;

    @Column(name = "registerd_count", nullable = false)
    private int registeredCount;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "insert_dt", nullable = false, updatable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsDelete isDelete;

    @Version
    private Long version;  // 낙관적 락을 위한 버전 필드

    public LectureSchedule(Long lectureId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.lectureId = lectureId;
        this.limitedCount = 30;
        this.registeredCount = 0;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.insertDt = LocalDateTime.now();
        this.updateDt = LocalDateTime.now();
        this.isDelete = IsDelete.N;
    }

    public LectureSchedule(Long lectureId, int limitedCount, int registeredCount, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.lectureId = lectureId;
        this.limitedCount = limitedCount;
        this.registeredCount = registeredCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.insertDt = LocalDateTime.now();
        this.updateDt = LocalDateTime.now();
        this.isDelete = IsDelete.N;
    }

    public boolean isOverStartDateTime() {
        return LocalDateTime.now().isAfter(startDateTime);
    }

    public void increaseRegisterCount() {
        if (this.limitedCount <= this.registeredCount) {
            throw new IllegalArgumentException("신청 가능한 인원을 초과하였습니다.");
        }
        this.registeredCount++;
    }
}
