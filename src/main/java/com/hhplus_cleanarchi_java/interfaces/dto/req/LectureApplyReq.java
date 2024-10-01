package com.hhplus_cleanarchi_java.interfaces.dto.req;

import jakarta.validation.constraints.NotNull;

public record LectureApplyReq(
        @NotNull
        Long userId,
        @NotNull
        Long lectureScheduleId
) {
}
