package com.asc2526.da.unit5.vtschool_rest_api.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Subject;
import com.asc2526.da.unit5.vtschool_rest_api.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getSubjects(
            @RequestParam(required = false) Integer courseId
    ) {
        if (courseId != null) {
            return subjectService.findByCourse(courseId);
        }
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable Integer id) {
        return subjectService.findById(id);
    }
}
