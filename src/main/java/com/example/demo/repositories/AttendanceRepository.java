package com.example.demo.repositories;

import com.example.demo.domains.lessons.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
