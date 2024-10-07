package com.hhplus_cleanarchi_java.app.domain.lecture.service;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureScheduleRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureRegistrationService {
    private final LectureRegistrationRepository lectureRegistrationRepository; // 특강 신청 정보를 관리하는 레포지토리
    private final LectureScheduleRepository lectureScheduleRepository; // 특강 스케줄 정보를 관리하는 레포지토리

    @Retryable(
            retryFor = {ObjectOptimisticLockingFailureException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 200)
    )
    @Transactional
    public LectureRegistration apply(long lectureScheduleId, long userId) {
        // 스케줄 조회
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(lectureScheduleId);

        // 중복 신청 체크
        checkDuplicateRegistration(userId, lectureSchedule.getId());

        // 강의 시작 시간 체크
        validateOverStartDateTime(lectureSchedule);

        // 등록 인원 증가
        lectureSchedule.increaseRegisterCount();

        // 특강 신청 저장
        LectureRegistration lectureRegistration = new LectureRegistration(lectureSchedule.getId(), userId);
        lectureRegistrationRepository.save(lectureRegistration);

        return lectureRegistration;
    }

    // 중복 등록 검증 메서드: 사용자가 이미 해당 강의 스케줄에 등록되었는지 확인
    private void checkDuplicateRegistration(long userId, long lectureScheduleId) {
        // 사용자 ID와 강의 스케줄 ID로 기존의 등록 내역을 조회
        LectureRegistrations lectureRegistrations = new LectureRegistrations(lectureRegistrationRepository.findBy(lectureScheduleId, userId));

        // 조회된 등록 내역 중 동일한 스케줄에 이미 신청한 기록이 있는지 확인
        lectureRegistrations.validateDuplicateRegistration(userId, lectureScheduleId);
    }

    private void validateOverStartDateTime(LectureSchedule lectureSchedule) {
        if (lectureSchedule.isOverStartDateTime()) {
            throw new RuntimeException("특강 시작일자가 이미 지났습니다.");
        }
    }

    // 일급 컬렉션 일급 객체
    public record LectureRegistrations(List<LectureRegistration> lectureRegistrationList) {
        public void validateDuplicateRegistration(long userId, long lectureScheduleId) {
            for (LectureRegistration lectureRegistration : lectureRegistrationList()) {
                if (lectureRegistration.idDuplicate(userId, lectureScheduleId)) {
                    throw new RuntimeException("이미 신청한 특강입니다.");
                }
            }
        }
    }
}
