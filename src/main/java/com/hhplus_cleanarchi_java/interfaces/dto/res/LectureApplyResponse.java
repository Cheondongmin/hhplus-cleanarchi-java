package com.hhplus_cleanarchi_java.interfaces.dto.res;

import jakarta.validation.constraints.NotNull;

public record LectureApplyResponse(
        @NotNull
        Long userId,
        @NotNull
        Long lectureScheduleId
) {
}
