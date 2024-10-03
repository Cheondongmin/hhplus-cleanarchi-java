package com.hhplus_cleanarchi_java.domain;

import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.app.domain.lecture.service.LectureRegistrationService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LectureRegistrationsTest {
    @Test
    void 동일한_사용자가_같은_특강에_이미_신청했을_경우_예외_발생() {
        // given: 사용자가 이미 특정 특강에 등록된 상태
        long userId = 1L;
        long lectureScheduleId = 101L;
        LectureRegistration existingRegistration = new LectureRegistration(lectureScheduleId, userId);
        LectureRegistrationService.LectureRegistrations registrations = new LectureRegistrationService.LectureRegistrations(List.of(existingRegistration));

        // when & then: 중복 신청 시도 시 예외 발생
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrations.validateDuplicateRegistration(userId, lectureScheduleId));

        // 예외 메시지가 올바른지 검증
        assertEquals("이미 신청한 특강입니다.", exception.getMessage());
    }

    @Test
    void 다른_사용자_또는_다른_특강일_경우_중복_신청_아님() {
        // given: 다른 사용자가 해당 특강에 신청된 상태
        long userId = 1L;
        long lectureScheduleId = 101L;
        long otherUserId = 2L;
        long otherLectureScheduleId = 102L;

        LectureRegistration existingRegistration = new LectureRegistration(lectureScheduleId, otherUserId);  // 다른 사용자
        LectureRegistration anotherRegistration = new LectureRegistration(otherLectureScheduleId, userId);  // 다른 특강
        LectureRegistrationService.LectureRegistrations registrations = new LectureRegistrationService.LectureRegistrations(List.of(existingRegistration, anotherRegistration));

        // when & then: 동일하지 않은 경우 예외 발생하지 않음
        registrations.validateDuplicateRegistration(userId, lectureScheduleId);  // 예외 발생하지 않음
    }

    @Test
    void 여러_등록_내역중_중복된_신청이_있을_경우_예외_발생() {
        // given: 여러 등록 내역이 있지만, 특정 사용자와 특강 스케줄에 중복된 신청이 있는 경우
        long userId = 1L;
        long lectureScheduleId = 101L;

        LectureRegistration registration1 = new LectureRegistration(102L, userId);  // 다른 특강
        LectureRegistration registration2 = new LectureRegistration(lectureScheduleId, userId);  // 중복된 특강
        LectureRegistration registration3 = new LectureRegistration(103L, userId);  // 또 다른 특강
        LectureRegistrationService.LectureRegistrations registrations = new LectureRegistrationService.LectureRegistrations(List.of(registration1, registration2, registration3));

        // when & then: 중복 신청 시도 시 예외 발생
        Exception exception = assertThrows(RuntimeException.class,
                () -> registrations.validateDuplicateRegistration(userId, lectureScheduleId));

        // 예외 메시지가 올바른지 검증
        assertEquals("이미 신청한 특강입니다.", exception.getMessage());
    }

}
