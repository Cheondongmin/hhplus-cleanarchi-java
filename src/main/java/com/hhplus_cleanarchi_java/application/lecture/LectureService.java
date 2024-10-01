package com.hhplus_cleanarchi_java.application.lecture;

import com.hhplus_cleanarchi_java.domain.lecture.LectureInfo;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.domain.lecture.service.LectureRegistrations;
import com.hhplus_cleanarchi_java.interfaces.dto.res.LectureApplyRes;
import com.hhplus_cleanarchi_java.interfaces.dto.res.LectureInfoApplicationRes;
import com.hhplus_cleanarchi_java.interfaces.dto.res.LectureRegisterResultRes;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final LectureRegistrations lectureRegistrations; // 특강 신청 비즈니스 로직 관리 클래스

    /* @Retryable:
        Spring의 spring-retry 라이브러리에서 제공하는 어노테이션입니다.
        특정 예외가 발생할 경우, 지정된 횟수만큼 재시도를 하도록 설정할 수 있습니다.
        여기서 retryFor는 재시도할 예외를 지정하며, maxAttempts는 최대 재시도 횟수를, backoff는 재시도 사이의 딜레이를 설정합니다.
        이 경우, ObjectOptimisticLockingFailureException 예외가 발생하면 최대 10번까지 20ms의 딜레이로 재시도하게 됩니다.
    */
    @Retryable(
            retryFor = {ObjectOptimisticLockingFailureException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 200)
    )
    @Transactional
    public LectureApplyRes apply(long lectureScheduleId, long userId) {
        LectureRegistration lectureRegistration = lectureRegistrations.register(lectureScheduleId, userId);
        lectureRegistrationRepository.save(lectureRegistration);
        return new LectureApplyRes(lectureRegistration.getUserId(), lectureRegistration.getLectureScheduleId());
    }

    @Transactional(readOnly = true)
    public LectureRegisterResultRes hasUserAppliedForLecture(long lectureScheduleId, long userId) {
        List<LectureRegistration> lectureRegistrationList = lectureRegistrationRepository.findBy(lectureScheduleId, userId);
        return new LectureRegisterResultRes(!lectureRegistrationList.isEmpty());
    }

    @Transactional(readOnly = true)
    public List<LectureInfoApplicationRes> findLectures() {
        List<LectureInfo> lectureInfos = lectureRepository.findAllLectureInfo();
        return LectureInfoApplicationRes.from(lectureInfos);
    }
}
