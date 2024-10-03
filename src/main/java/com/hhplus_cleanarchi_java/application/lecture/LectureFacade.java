package com.hhplus_cleanarchi_java.application.lecture;

import com.hhplus_cleanarchi_java.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.domain.lecture.dto.LectureRegisterInfo;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureScheduleRepository;
import com.hhplus_cleanarchi_java.domain.lecture.service.LectureRegistrationService;
import com.hhplus_cleanarchi_java.domain.lecture.service.LectureSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureRepository lectureRepository;
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final LectureRegistrationService lectureRegistrationService;
    private final LectureSelectService lectureSelectService;

    @Retryable(
            retryFor = {ObjectOptimisticLockingFailureException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 200)
    )
    @Transactional
    public LectureRegistration apply(long lectureScheduleId, long userId) {
        // 특강 신청 로직
        LectureRegistration lectureRegistration = lectureRegistrationService.register(lectureScheduleId, userId);
        lectureRegistrationRepository.save(lectureRegistration);
        return lectureRegistration;
    }

    @Transactional(readOnly = true)
    public List<LectureInfo> findLectures() {
        // 특강 정보를 조회
        return lectureRepository.findAllLectureInfo();
    }

    @Transactional(readOnly = true)
    public List<LectureRegisterInfo> selectUserRegistrationList(Long userId) {
        // 유저의 등록된 특강 리스트를 반환
        return lectureSelectService.selectRegisterInfoByUserId(userId);
    }

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
