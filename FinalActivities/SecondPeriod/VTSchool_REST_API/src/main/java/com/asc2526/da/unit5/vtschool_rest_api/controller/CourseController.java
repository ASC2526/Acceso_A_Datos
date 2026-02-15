package com.asc2526.da.unit5.vtschool_rest_api.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Course;
import com.asc2526.da.unit5.vtschool_rest_api.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.findById(id);
    }
}
