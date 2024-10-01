package com.hhplus_cleanarchi_java.interfaces.contoller;

import com.hhplus_cleanarchi_java.application.lecture.LectureService;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureAddReq;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureApplyReq;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureScheduleAddReq;
import com.hhplus_cleanarchi_java.interfaces.dto.res.*;
import jakarta.validation.Valid;
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
    public LectureApplyRes apply(@RequestBody @Valid LectureApplyReq req) {
        return lectureService.apply(req.lectureScheduleId(), req.userId());
    }

    /*
     * 특강 신청 완료 여부 조회 API
     */
    @GetMapping("/application/{lectureScheduleId}/{userId}")
    public LectureRegisterResultRes hasUserAppliedForLecture(@PathVariable Long lectureScheduleId, @PathVariable Long userId) {
        return lectureService.hasUserAppliedForLecture(lectureScheduleId, userId);
    }

    /**
     * 특강 등록 API
     */
    @PostMapping("/add")
    public LectureAddRes insertLecture(@RequestBody @Valid LectureAddReq req) {
        return lectureService.insertLecture(req.lectureName());
    }

    /**
     * 특강 스케쥴 등록 API
     */
    @PostMapping("/schedule/add")
    public LectureScheduleAddRes insertLectureSchedule(@RequestBody @Valid LectureScheduleAddReq req) {
        return lectureService.insertLectureSchedule(req.lectureId(), req.limitedCount(), req.startDateTime(), req.endDateTime());
    }

    /**
     * 특강 조회 API
     */
    @GetMapping
    public List<LectureInfoApplicationRes> findLectures() {
        return lectureService.findLectures();
    }
}
