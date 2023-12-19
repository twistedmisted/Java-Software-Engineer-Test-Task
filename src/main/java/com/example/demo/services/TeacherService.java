package com.example.demo.services;

import com.example.demo.domains.Studio;
import com.example.demo.domains.lessons.Lesson;
import com.example.demo.domains.users.Teacher;
import com.example.demo.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "teachers")
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Cacheable
    public Teacher getById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow();
    }

    @Cacheable
    public Teacher getByUsername(String username) {
        return teacherRepository.findByUsername(username).orElseThrow();
    }

    @Cacheable(cacheNames = "teacher-lessons", key = "#teacherId")
    public Set<Lesson> getAllLessons(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow().getLessons();
    }

    @CachePut(key = "#teacherId")
    public Teacher updateTeacherStudio(Set<Studio> studios, Long teacherId) {
        var teacher = teacherRepository.findById(teacherId).orElseThrow();
        teacher.setStudios(studios);
        return teacherRepository.save(teacher);
    }
}
