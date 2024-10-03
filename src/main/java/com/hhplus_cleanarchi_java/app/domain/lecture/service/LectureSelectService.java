package com.hhplus_cleanarchi_java.app.domain.lecture.service;

import com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureRegistrationRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureScheduleRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureRegisterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureSelectService {
    // 특강 조회와 관련된 중요한 비즈니스 규칙을 한 곳에서 관리하는 도메인 서비스 클래스
    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureRepository lectureRepository;

    @Transactional(readOnly = true)
    public List<LectureInfo> findAllLectures() {
        return lectureRepository.findAllLectureInfo();
    }

    @Transactional(readOnly = true)
    public List<LectureRegisterInfo> selectUserRegistrationList(Long userId) {
        // 유저의 등록된 특강 리스트를 반환
        return selectRegisterInfoByUserId(userId);
    }

    // 특강 신청한 내역 조회
    public List<LectureRegisterInfo> selectRegisterInfoByUserId(long userId) {
        // 사용자 ID로 해당 사용자가 신청한 모든 특강 등록 정보를 가져옴
        List<LectureRegistration> lectureRegistrationList = lectureRegistrationRepository.findByUserId(userId);

        // 결과를 담을 LectureRegisterResultRes 리스트
        List<LectureRegisterInfo> resultList = new ArrayList<>();

        // 각 LectureRegistration에서 필요한 정보를 추출하여 LectureRegisterResultRes로 변환
        for (LectureRegistration registration : lectureRegistrationList) {
            // LectureSchedule 정보를 가져옴
            LectureSchedule lectureSchedule = lectureScheduleRepository.findById(registration.getLectureScheduleId());

            // Lecture 정보를 가져옴
            Lecture lecture = lectureRepository.findById(lectureSchedule.getLectureId());

            // LectureRegisterResultRes 생성하여 결과 리스트에 추가
            resultList.add(new LectureRegisterInfo(
                    lectureSchedule.getId(),
                    lecture.getName(),
                    lecture.getTeacher(),
                    lectureSchedule.getStartDateTime().toString(),
                    lectureSchedule.getEndDateTime().toString()
            ));
        }

        return resultList;
    }
}
