package com.hhplus_cleanarchi_java.interfaces.contoller;

import com.hhplus_cleanarchi_java.application.lecture.LectureService;
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

    private final LectureService lectureService;

    /*
     * 특강 신청 API
     */
    @PostMapping("/apply")
    public LectureApplyRes apply(@RequestBody LectureApplyReq req) {
        LectureRegistration registration = lectureService.apply(req.lectureScheduleId(), req.userId());
        return new LectureApplyRes(registration.getUserId(), registration.getLectureScheduleId());
    }

    /**
     * 특강 선택 조회 API
     */
    @GetMapping
    public List<LectureInfoApplicationRes> findLectures() {
        return LectureInfoApplicationRes.from(lectureService.findLectures());
    }

    /*
     * 특강 신청 완료 리스트 조회 API
     */
    @GetMapping("/application/{userId}")
    public List<LectureRegisterResultRes> selectUserRegistrationList(@PathVariable Long userId) {
        return LectureRegisterResultRes.from(lectureService.selectUserRegistrationList(userId));
    }

    /**
     * 특강 등록 API
     */
    @PostMapping("/add")
    public LectureAddRes insertLecture(@RequestBody LectureAddReq req) {
        Lecture lecture = lectureService.insertLecture(req.lectureName(), req.teacherName());
        return new LectureAddRes(lecture.getId(), lecture.getName(), lecture.getTeacher());
    }

    /**
     * 특강 스케쥴 등록 API
     */
    @PostMapping("/schedule/add")
    public LectureScheduleAddRes insertLectureSchedule(@RequestBody LectureScheduleAddReq req) {
        LectureSchedule schedule = lectureService.insertLectureSchedule(req.lectureId(), req.startDateTime(), req.endDateTime());
        return new LectureScheduleAddRes(schedule.getId(), schedule.getStartDateTime().toString(), schedule.getEndDateTime().toString());
    }
}
