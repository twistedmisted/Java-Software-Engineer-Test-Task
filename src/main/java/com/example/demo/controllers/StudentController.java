package com.example.demo.controllers;

import com.example.demo.domains.users.Student.Student;
import com.example.demo.domains.users.Student.accounts.TransactionRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final TransactionRepository transactionRepository;

    @GetMapping("/get-paged")
    List<Student> getStudentsPaged() {
        return studentService.getAll();
    }

    @GetMapping("/get/{studentId}")
    Student getStudent(@PathVariable Long studentId) {
        return studentService.getById(studentId);
    }

    @DeleteMapping("/remove-transaction")
    void removeTransaction(@RequestParam(name = "transactionId") long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}

