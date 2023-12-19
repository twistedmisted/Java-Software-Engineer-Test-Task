package com.example.demo.controllers;

import com.example.demo.domains.lessons.Attendance;
import com.example.demo.domains.lessons.Lesson;
import com.example.demo.domains.lessons.PlainLesson;
import com.example.demo.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequiredArgsConstructor
public class LessonsController {

    private final LessonService lessonService;

    @GetMapping("/get/{lessonId}")
    Lesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getById(lessonId);
    }

    @DeleteMapping("/cancel/{lessonId}")
    Attendance cancelLesson(@PathVariable Long lessonId,
                            @RequestParam(name = "studentId") Long studentId,
                            @RequestBody(required = false) String reason,
                            Authentication authentication) {
        return lessonService.cancelLesson(lessonId, studentId, reason, authentication);
    }

    @PutMapping("/update")
    Lesson updateLesson(@RequestBody PlainLesson lesson) {
        return lessonService.editLesson(lesson);
    }

}