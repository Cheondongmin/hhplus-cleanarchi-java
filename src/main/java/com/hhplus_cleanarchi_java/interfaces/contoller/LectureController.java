package com.hhplus_cleanarchi_java.interfaces.contoller;

import com.hhplus_cleanarchi_java.application.lecture.LectureFacade;
import com.hhplus_cleanarchi_java.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureRegistration;
import com.hhplus_cleanarchi_java.domain.lecture.entity.LectureSchedule;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureAddReq;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureApplyReq;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureScheduleAddReq;
import com.hhplus_cleanarchi_java.interfaces.dto.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /*
     * 특강 신청 API
     */
    @PostMapping("/apply")
    public LectureApplyRes apply(@RequestBody LectureApplyReq req) {
        LectureRegistration registration = lectureFacade.apply(req.lectureScheduleId(), req.userId());
        return new LectureApplyRes(registration.getUserId(), registration.getLectureScheduleId());
    }

    /**
     * 특강 선택 조회 API
     */
    @GetMapping
    public List<LectureInfoApplicationRes> findLectures() {
        return LectureInfoApplicationRes.from(lectureFacade.findLectures());
    }

    /*
     * 특강 신청 완료 리스트 조회 API
     */
    @GetMapping("/application/{userId}")
    public List<LectureRegisterResultRes> selectUserRegistrationList(@PathVariable Long userId) {
        return LectureRegisterResultRes.from(lectureFacade.selectUserRegistrationList(userId));
    }

    /**
     * 특강 등록 API
     */
    @PostMapping("/add")
    public LectureAddRes insertLecture(@RequestBody LectureAddReq req) {
        Lecture lecture = lectureFacade.insertLecture(req.lectureName(), req.teacherName());
        return new LectureAddRes(lecture.getId(), lecture.getName(), lecture.getTeacher());
    }

    /**
     * 특강 스케쥴 등록 API
     */
    @PostMapping("/schedule/add")
    public LectureScheduleAddRes insertLectureSchedule(@RequestBody LectureScheduleAddReq req) {
        LectureSchedule schedule = lectureFacade.insertLectureSchedule(req.lectureId(), req.startDateTime(), req.endDateTime());
        return new LectureScheduleAddRes(schedule.getId(), schedule.getStartDateTime().toString(), schedule.getEndDateTime().toString());
    }
}
