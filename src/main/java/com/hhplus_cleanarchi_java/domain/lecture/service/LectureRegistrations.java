package com.hhplus_cleanarchi_java.domain.lecture.service;

import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureRegistrations {
    // 특강 신청과 관련된 중요한 비즈니스 규칙을 한 곳에서 관리하는 도메인 서비스 클래스
    // 여러 도메인 엔티티 간의 상호작용을 관리하고, 중복 신청 방지, 특강 시작 시간 확인 등의 검증을 처리함
    private final LectureRegistrationRepository lectureRegistrationRepository; // 특강 신청 정보를 관리하는 레포지토리
    private final LectureScheduleRepository lectureScheduleRepository; // 특강 스케줄 정보를 관리하는 레포지토리

    // 특강 등록 메서드: 사용자가 특정 특강 일정에 대해 신청할 때 호출
    public LectureRegistration register(long lectureScheduleId, long userId) {
        // 등록된 강의 스케줄 조회
        // lectureScheduleId를 이용해 데이터베이스에서 해당 강의 스케줄 정보를 조회
        // 스케줄이 존재하지 않으면 예외가 발생할 수 있음
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(lectureScheduleId);

        // 사용자가 이미 해당 강의 스케줄에 신청했는지 확인
        // 중복 신청을 방지하기 위해, 사용자가 동일한 강의 스케줄에 이미 등록되어 있는지 검증
        checkDuplicateRegistration(userId, lectureSchedule.getId());

        // 강의가 이미 시작된 경우 신청을 막기 위해 시작 시간을 확인
        // 특강의 시작 시간이 현재 시간보다 앞서 있는지 확인하고, 이미 시작된 경우 예외 발생
        validateOverStartDateTime(lectureSchedule);

        // 등록 인원 증가
        // 특강의 제한 인원에 도달했는지 확인하고, 아직 여유가 있으면 등록 인원을 1 증가시킴
        // 만약 제한 인원을 초과할 경우 IllegalArgumentException을 던져 더 이상 신청이 불가능하도록 함
        lectureSchedule.increaseRegisterCount();

        // 새로 등록된 특강 신청 정보를 생성하여 반환
        // 사용자 ID와 강의 스케줄 ID를 사용해 LectureRegistration 객체를 생성하고 반환
        return new LectureRegistration(lectureSchedule.getId(), userId);
    }

    // 중복 등록 검증 메서드: 사용자가 이미 해당 강의 스케줄에 등록되었는지 확인
    private void checkDuplicateRegistration(long userId, long lectureScheduleId) {
        // 사용자 ID와 강의 스케줄 ID로 기존의 등록 내역을 조회
        List<LectureRegistration> lectureRegistrationList = lectureRegistrationRepository.findBy(lectureScheduleId, userId);

        // 조회된 등록 내역 중 동일한 스케줄에 이미 신청한 기록이 있는지 확인
        boolean isDuplicate = false;
        for(LectureRegistration lectureRegistration : lectureRegistrationList) {
            if(lectureRegistration.getUserId() == userId && lectureRegistration.getLectureScheduleId() == lectureScheduleId) {
                isDuplicate = true;
                break;
            }
        }

        // 만약 중복 신청이 확인되면 예외 발생
        if (isDuplicate) {
            throw new RuntimeException("이미 신청한 특강입니다.");
        }
    }

    // 특강 시작 시간 검증 메서드: 강의가 이미 시작되었는지 확인
    private void validateOverStartDateTime(LectureSchedule lectureSchedule) {
        // 현재 시간과 강의 시작 시간을 비교해 이미 시작된 경우 예외 발생
        if (lectureSchedule.isOverStartDateTime()) {
            throw new RuntimeException("특강 시작일자가 이미 지났습니다.");
        }
    }
}
