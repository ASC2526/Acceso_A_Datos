package com.asc2526.da.unit5.vtschool_rest_api.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{idcard}")
    public Student getStudentById(@PathVariable String idcard) {
        return studentService.findById(idcard);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Student> createStudents(
           @Valid @RequestBody List<Student> students
    ) {
        return studentService.create(students);
    }


    @PutMapping("/{idcard}")
    public Student updateStudent(
            @PathVariable String idcard,
            @Valid @RequestBody Student student
    ) {
        return studentService.update(idcard, student);
    }


    @DeleteMapping("/{idcard}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable String idcard) {
        studentService.delete(idcard);
    }
}
