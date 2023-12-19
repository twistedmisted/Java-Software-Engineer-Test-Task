package com.example.demo.repositories;

import com.example.demo.domains.lessons.LessonCredit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonCreditRepository extends JpaRepository<LessonCredit, Long> {
}
