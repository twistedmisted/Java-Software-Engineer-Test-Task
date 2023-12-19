package com.example.demo.repositories;

import com.example.demo.domains.lessons.PlainLesson;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainLessonRepository extends JpaRepository<PlainLesson, Long> {
    void deleteById (@NotNull Long id);
}
