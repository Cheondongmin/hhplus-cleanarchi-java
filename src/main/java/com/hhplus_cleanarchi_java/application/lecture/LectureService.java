package com.hhplus_cleanarchi_java.application.lecture;

import com.hhplus_cleanarchi_java.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
}
