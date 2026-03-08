package com.vtschool2526.service;

import com.vtschool2526.model.Score;
import com.vtschool2526.model.api.ApiManager;

import java.util.List;

public class PrintService {

    public static void print(String studentId, Integer courseId) {

        try {

            ApiManager apiManager = new ApiManager();
            List<Score> scores = apiManager.getScores(studentId, courseId);

            if (scores.isEmpty()) {
                System.out.println("No scores found.");
                return;
            }

            for (Score s : scores) {
                System.out.println(
                        "Subject: " + s.getSubjectId() +
                                " | Score: " + s.getScore()
                );
            }

        } catch (Exception e) {

            System.out.println("API ERROR:");
            System.out.println(e.getMessage());
        }
    }
}