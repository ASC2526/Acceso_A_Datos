//package com.vtschool2526.service;
//
//import com.vtschool2526.model.Enrollment;
//import com.vtschool2526.model.api.ApiManager;
//
//import java.util.List;
//
//public class DiplomaService {
//
//    public static void showDiploma(String studentId, Integer courseId) {
//
//        try {
//
//            ApiManager apiManager = new ApiManager();
//
//            List<Enrollment> enrollments =
//                    apiManager.getEnrollments(studentId, courseId, null);
//
//            if (enrollments.isEmpty()) {
//                System.out.println("No enrollment found.");
//                return;
//            }
//
//            Enrollment enrollment = enrollments.get(0);
//
//            System.out.println("=================================");
//            System.out.println("VT INSTITUTE");
//            System.out.println("=================================");
//            System.out.println("Student: " + studentId);
//            System.out.println("Course: " + courseId);
//            System.out.println("Year: " + enrollment.getYear());
//            System.out.println("=================================");
//
//        } catch (Exception e) {
//
//            System.out.println("API ERROR:");
//            System.out.println(e.getMessage());
//        }
//    }
//}