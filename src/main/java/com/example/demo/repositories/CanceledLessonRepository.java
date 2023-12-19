package com.example.demo.repositories;

import com.example.demo.domains.lessons.CanceledLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanceledLessonRepository extends JpaRepository<CanceledLesson, Long> {
}