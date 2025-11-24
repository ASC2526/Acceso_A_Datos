package com.vtschool2526.service;

import com.vtschool2526.model.Course;
import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.Score;
import com.vtschool2526.model.Student;
import com.vtschool2526.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class DiplomaService {
    public static void printDiploma(String idcard, int courseId) {

        try (Session session = HibernateSession.openSession()) {

            Student st = session.find(Student.class, idcard);
            if (st == null) {
                System.out.println("ERROR → Student not found: " + idcard);
                return;
            }

            Course c = session.find(Course.class, courseId);
            if (c == null) {
                System.out.println("ERROR → Course not found: " + courseId);
                return;
            }

            Query<Enrollment> q = session.createQuery(
                    "from Enrollment e " +
                            "where e.student.idcard = :sid and e.course.id = :cid " +
                            "order by e.year desc",
                    Enrollment.class);

            q.setParameter("sid", idcard);
            q.setParameter("cid", courseId);

            int year = q.list().get(0).getYear();
            Enrollment enroll = q.list().get(0);

            if (enroll == null) {
                System.out.println("The student " + idcard + " has not finished " + courseId);
            }

            System.out.println(courseId + " " + (year));

        } catch (Exception e) {
            System.out.println("ERROR --> Import aborted.");
        }
    }
}

