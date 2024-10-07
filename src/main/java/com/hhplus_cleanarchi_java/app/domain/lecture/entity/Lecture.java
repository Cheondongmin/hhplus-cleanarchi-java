package com.hhplus_cleanarchi_java.app.domain.lecture.entity;

import com.hhplus_cleanarchi_java.app.domain.enums.IsDelete;
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
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "teacher", nullable = false)
    private String teacher;

    @Column(name = "insert_dt", nullable = false, updatable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsDelete isDelete;

    public Lecture(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
        this.insertDt = LocalDateTime.now();
        this.updateDt = LocalDateTime.now();
        this.isDelete = IsDelete.N;
    }
}

