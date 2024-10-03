package com.hhplus_cleanarchi_java.app.domain.lecture.entity;

import com.hhplus_cleanarchi_java.app.domain.enums.IsDelete;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_dt", nullable = false, updatable = false)
    private LocalDateTime insertDt;

    @Column(name = "update_dt", nullable = false)
    private LocalDateTime updateDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", nullable = false)
    private IsDelete isDelete;
}
