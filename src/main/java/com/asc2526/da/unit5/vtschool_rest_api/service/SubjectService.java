package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Subject;
import com.asc2526.da.unit5.vtschool_rest_api.exception.CourseNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.SubjectNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.repository.CourseRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    public SubjectService(
            SubjectRepository subjectRepository,
            CourseRepository courseRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public List<Subject> findByCourse(Integer courseId) {

        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        return subjectRepository.findByCourseId(courseId);
    }
}
