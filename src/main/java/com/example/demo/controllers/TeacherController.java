package com.example.demo.controllers;

import com.example.demo.domains.Studio;
import com.example.demo.domains.lessons.Lesson;
import com.example.demo.domains.users.Teacher;
import com.example.demo.repositories.TeacherRepository;
import com.example.demo.security.user.JwtUserService;
import com.example.demo.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherRepository teacherRepository;

    private final JwtUserService userService;

    @GetMapping("/get")
    List<Teacher> getTeachers() {
        return teacherService.getAll();
    }

    @GetMapping("/get/{teacherId}")
    Teacher getTeacher(@PathVariable Long teacherId) {
        return teacherService.getById(teacherId);
    }

    @GetMapping("/get/myself")
    Teacher getMyself(Authentication authentication) {
        return teacherService.getByUsername(String.valueOf(authentication.getPrincipal()));
    }

    @GetMapping("/get/schedule/{teacherId}")
    Set<Lesson> getTeacherSchedule(@PathVariable Long teacherId) {
        return teacherService.getAllLessons(teacherId);
    }

    @PostMapping("/create")
    Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/update-studio/{teacherId}")
    Teacher createTeacher(@RequestBody Set<Studio> studios, @PathVariable Long teacherId) {
        return teacherService.updateTeacherStudio(studios, teacherId);
    }
}
