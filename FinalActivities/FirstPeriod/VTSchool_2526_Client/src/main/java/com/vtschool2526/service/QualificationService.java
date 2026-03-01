package com.vtschool2526.service;

import com.vtschool2526.model.Score;
import com.vtschool2526.model.api.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QualificationService {

    public static void qualify(String studentId, Integer courseId) {

        try {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enrollment ID: ");
            Integer enrollmentId = Integer.parseInt(scanner.nextLine());

            System.out.print("Subject ID: ");
            Integer subjectId = Integer.parseInt(scanner.nextLine());

            System.out.print("Score: ");
            Integer value = Integer.parseInt(scanner.nextLine());

            Score score = new Score();
            score.setEnrollmentId(enrollmentId);
            score.setSubjectId(subjectId);
            score.setScore(value);

            List<Score> scores = new ArrayList<>();
            scores.add(score);

            ApiManager apiManager = new ApiManager();
            apiManager.saveScores(scores);

            System.out.println("Score saved successfully.");

        } catch (Exception e) {

            System.out.println("API ERROR:");
            System.out.println(e.getMessage());
        }
    }
}