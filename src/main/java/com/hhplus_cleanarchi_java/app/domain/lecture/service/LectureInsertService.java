package com.hhplus_cleanarchi_java.app.domain.lecture.service;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureScheduleRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LectureInsertService {
    private final LectureRepository lectureRepository;
    private final LectureScheduleRepository lectureScheduleRepository;

    @Transactional
    public Lecture insertLecture(String lectureName, String teacherName) {
        // 새로운 특강을 추가
        Lecture lecture = new Lecture(lectureName, teacherName);
        lectureRepository.save(lecture);
        return lecture;
    }

    @Transactional
    public LectureSchedule insertLectureSchedule(Long lectureId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // 새로운 특강 스케줄을 추가
        Lecture lecture = lectureRepository.findById(lectureId);
        LectureSchedule lectureSchedule = new LectureSchedule(lecture.getId(), startDateTime, endDateTime);
        lectureScheduleRepository.save(lectureSchedule);
        return lectureSchedule;
    }
}
