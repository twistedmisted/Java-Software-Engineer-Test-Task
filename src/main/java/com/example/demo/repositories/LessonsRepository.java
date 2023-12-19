package com.example.demo.repositories;

import com.example.demo.domains.lessons.Lesson;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDate;
import java.util.List;

public interface LessonsRepository extends JpaRepository<Lesson, Long>, QueryByExampleExecutor<Lesson> {

    @Query("SELECT l FROM Lesson l LEFT JOIN l.attendance a WHERE a.student.id = :studentId")
    List<Lesson> findAllByStudentId(@Param("studentId") Long students_id);

    void deleteById(@NotNull Long id);

    List<Lesson> findByDateBetween(LocalDate date, LocalDate date2);
}
