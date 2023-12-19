package com.example.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {

    @CacheEvict(cacheNames = "students", allEntries = true)
    public void clearStudentCache() {
        log.info("Students cache clearing");
    }

    @CacheEvict(cacheNames = "teachers", allEntries = true)
    public void clearTeacherCache() {
        log.info("Teachers cache clearing");
    }

    @CacheEvict(cacheNames = "teacher-lessons", allEntries = true)
    public void clearTeacherLessonsCache() {
        log.info("Teacher lessons cache clearing");
    }

    @CacheEvict(cacheNames = "lessons", allEntries = true)
    public void clearLessonsCache() {
        log.info("Lessons cache clearing");
    }
}
