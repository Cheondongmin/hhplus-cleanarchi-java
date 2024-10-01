package com.hhplus_cleanarchi_java.interfaces.contoller;

import com.hhplus_cleanarchi_java.application.lecture.LectureService;
import com.hhplus_cleanarchi_java.interfaces.dto.req.LectureApplyReq;
import com.hhplus_cleanarchi_java.interfaces.dto.res.LectureApplyRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /*
    * 특강 신청 API
    * */
    @PostMapping("/apply")
    public LectureApplyRes apply(@RequestBody @Valid LectureApplyReq req) {
        return lectureService.apply(req.lectureScheduleId(), req.userId());
    }
}
