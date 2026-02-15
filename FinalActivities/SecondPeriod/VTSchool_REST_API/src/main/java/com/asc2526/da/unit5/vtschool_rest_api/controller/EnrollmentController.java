package com.asc2526.da.unit5.vtschool_rest_api.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment enrollStudent(@Valid @RequestBody Enrollment enrollment) {
        return enrollmentService.create(enrollment);
    }

    @GetMapping
    public List<Enrollment> getEnrollments(
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) Integer courseId,
            @RequestParam(required = false) Integer year
    ) {
        return enrollmentService.findEnrollments(studentId, courseId, year);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnrollmentById(@PathVariable Integer id) {
        enrollmentService.deleteById(id);
    }
}
