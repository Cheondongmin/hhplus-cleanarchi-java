package com.hhplus_cleanarchi_java.interfaces.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LectureScheduleAddReq(
        @NotNull
        Long lectureId,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // "yyyy-MM-dd HH:mm:ss" 형식
        LocalDateTime startDateTime,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // 동일한 형식으로 끝 시간 설정
        LocalDateTime endDateTime
) {
}
