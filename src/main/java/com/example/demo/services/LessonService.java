package com.example.demo.services;

import com.example.demo.domains.lessons.Attendance;
import com.example.demo.domains.lessons.CanceledLesson;
import com.example.demo.domains.lessons.Lesson;
import com.example.demo.domains.lessons.PlainLesson;
import com.example.demo.repositories.*;
import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.JwtUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "lessons")
public class LessonService {
    private final LessonsRepository lessonsRepository;

    private final PlainLessonRepository plainLessonRepository;

    private final CanceledLessonRepository canceledLessonRepository;

    private final AttendanceRepository attendanceRepository;

    private final StudentRepository studentRepository;

    private final JwtUserService jwtUserService;

    private final LessonCreditRepository lessonCreditRepository;

    public Lesson detectAndSetType(Lesson lesson) {
        // Its check is there only one student in this lesson
        // It should implement privateLesson, but it wasn't implemented yet
        var lessonAttendance = lesson.getAttendance();
        try {
            if (lessonAttendance.size() == 1) {
                Long studentId = -1L;
                for (Attendance value : lessonAttendance) {
                    studentId = value.getStudent().getId();
                }
                var lessonsSize = lessonsRepository.findAllByStudentId(studentId).size();
                if (!(lessonsSize >= 5)) {
                    lesson.setLessonType("CB" + (lessonsSize + 1));
                }
            }
        } catch (NullPointerException e) {
        } finally {
        }
        return lesson;
    }

    Attendance cancelLesson(Long lessonId, Long studentId, String reason) {
        Lesson lesson = lessonsRepository.findById(lessonId).orElseThrow();
        for (Attendance a : lesson.getAttendance()) {
            if (a.getStudent().getId().equals(studentId)) {
                var canceledLesson = CanceledLesson.builder()
                        .attendance(a)
                        .reason(reason)
                        .build();
                canceledLessonRepository.save(canceledLesson);
                a.setCanceledLesson(canceledLesson);
                return attendanceRepository.save(a);
            }
        }
        return null;
    }

    @CachePut(key = "#lessonId")
    public Attendance cancelLesson(Long lessonId, Long studentId, String reason, Authentication authentication) {
        JwtUser currentUser = jwtUserService.getCurrentUser();
        boolean isSuperAdmin = currentUser.getAuthorities().stream()
                .map(e -> Objects.equals(e.getAuthority(), "ROLE_SUPER_ADMIN"))
                .collect(Collectors.toSet())
                .contains(true);
        Lesson lesson = lessonsRepository.findById(lessonId).orElseThrow();
        if (!isSuperAdmin && !Objects.equals(currentUser.getId(), lesson.getTeacher().getId())) {
            throw new RuntimeException("Teacher don't have a right to create a lesson for another teacher");
        }
        return cancelLesson(lessonId, studentId, reason);
    }

    public List<Lesson> findAll() {
        return lessonsRepository.findAll();
    }

    @CachePut(key = "#lesson.id")
    public PlainLesson editLesson(PlainLesson lesson) {
        var mappedAttendances = ((lesson.getAttendance().stream()
                .map(attendant -> Attendance.builder()
                        .lesson(lesson)
                        .student(studentRepository.findById(attendant.getStudent().getId()).orElseThrow())
                        .build())
                .collect(Collectors.toSet())));
        Set<Attendance> savedAttendances = new HashSet<>(attendanceRepository.saveAll(mappedAttendances));
        lesson.setAttendance(savedAttendances);
        return plainLessonRepository.save(lesson);
    }

    public List<Lesson> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return lessonsRepository.findByDateBetween(startDate, endDate);
    }

    @Cacheable
    public Lesson getById(Long lessonId) {
        return lessonsRepository.findById(lessonId).orElseThrow();
    }
}
