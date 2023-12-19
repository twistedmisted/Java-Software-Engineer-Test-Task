package com.example.demo.repositories;

import com.example.demo.domains.users.Student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Page<Student> findAll(Specification<Student> specification, Pageable pageable);
}