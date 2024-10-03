package com.hhplus_cleanarchi_java.infra;

import com.hhplus_cleanarchi_java.IntegrationTest;
import com.hhplus_cleanarchi_java.app.domain.lecture.dto.LectureInfo;
import com.hhplus_cleanarchi_java.app.domain.lecture.entity.Lecture;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureRepository;
import com.hhplus_cleanarchi_java.app.domain.lecture.repository.LectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.hhplus_cleanarchi_java.fixture.LectureFixture.스프링_특강;
import static com.hhplus_cleanarchi_java.fixture.LectureFixture.자바_특강;
import static com.hhplus_cleanarchi_java.fixture.LectureScheduleFixture.특강_스케줄;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class LectureJpaRepositoryTest extends IntegrationTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Test
    void 등록된_특강_정보를_조회할_수_있다() {
        // given
        Lecture javaLecture = lectureRepository.save(자바_특강());
        Lecture springLecture = lectureRepository.save(스프링_특강());
        LocalDateTime now = LocalDateTime.now().withNano(0);
        LocalDateTime firstStartTime = now.plusHours(1L);
        LocalDateTime firstEndTime = now.plusHours(2L);
        LocalDateTime secondStartTime = now.plusHours(3L);
        LocalDateTime secondEndTime = now.plusHours(4L);

        특강_스케줄_등록(javaLecture, firstStartTime, firstEndTime);
        특강_스케줄_등록(javaLecture, secondStartTime, secondEndTime);
        특강_스케줄_등록(springLecture, firstStartTime, firstEndTime);
        특강_스케줄_등록(springLecture, secondStartTime, secondEndTime);

        // when
        List<LectureInfo> lectureInfos = lectureRepository.findAllLectureInfo();

        // then
        assertThat(lectureInfos).hasSize(4)
                .extracting("name", "startDateTime", "endDateTime")
                .containsExactly(
                        tuple("자바 특강", firstStartTime, firstEndTime),
                        tuple("자바 특강", secondStartTime, secondEndTime),
                        tuple("스프링 특강", firstStartTime, firstEndTime),
                        tuple("스프링 특강", secondStartTime, secondEndTime)
                );
    }

    private void 특강_스케줄_등록(Lecture lecture, LocalDateTime startTime, LocalDateTime endTime) {
        lectureScheduleRepository.save(특강_스케줄(
                lecture.getId(),
                0,
                startTime.withNano(0),
                endTime.withNano(0)
        ));
    }

}