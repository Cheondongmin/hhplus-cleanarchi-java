package com.hhplus_cleanarchi_java.interfaces.dto.req;

import jakarta.validation.constraints.NotNull;

public record LectureAddReq(
        @NotNull
        String lectureName,
        @NotNull
        String teacherName
) {
}
