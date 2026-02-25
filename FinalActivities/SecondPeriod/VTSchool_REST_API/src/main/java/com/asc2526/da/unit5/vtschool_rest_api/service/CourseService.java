package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Course;
import com.asc2526.da.unit5.vtschool_rest_api.exception.CourseNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }


    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Course id is required");
        }

        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public List<Course> findEnrolledCourses(String studentId) {
        return courseRepository.findCoursesEnrolledOfStudent(studentId);
    }

}
