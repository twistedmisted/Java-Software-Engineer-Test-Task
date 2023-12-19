package com.example.demo.services;

import com.example.demo.domains.lessons.Attendance;
import com.example.demo.domains.lessons.Lesson;
import com.example.demo.domains.users.Student.Student;
import com.example.demo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "students")
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Cacheable
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Set<Lesson> getLessons(Student student) {
        return student.getAttendance().stream().map(Attendance::getLesson).collect(Collectors.toSet());
    }

    public List<Student> getStudentsForStatistics() {
        return studentRepository.findAll().stream().peek((st) -> {
//            var lessons = st.getLessons().stream().filter(lsn -> lsn.getType() == LessonType.PLAIN || lsn.getType() == LessonType.CONFIDENCE_BUILDER).collect(Collectors.toSet());
            var lessons = getLessons(st);
            var attendance = lessons.stream().map(l -> Attendance.builder()
                    .lesson(l)
                    .student(st)
                    .build()).collect(Collectors.toSet());
            st.setAttendance(attendance);
        }).collect(Collectors.toList());
    }
}
