package com.vtschool2526.service;

import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.api.ApiManager;

import java.time.LocalDate;

public class EnrollmentService {

    public static void enroll(String studentId, Integer courseId) {

        try {

            ApiManager apiManager = new ApiManager();

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(studentId);
            enrollment.setCourseId(courseId);
            enrollment.setYear(LocalDate.now().getYear());

            apiManager.enrollStudent(enrollment);

            System.out.println("Enrollment created successfully.");

        } catch (Exception e) {

            System.out.println("API ERROR:");
            System.out.println(e.getMessage());
        }
    }
}