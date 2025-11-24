package com.vtschool2526.service;

import com.vtschool2526.model.Course;
import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.Score;
import com.vtschool2526.model.Student;
import com.vtschool2526.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class PrintService {

    public static void print(String idcard, int courseId) {

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

            // get all enrollments of this student in this course
            Query<Enrollment> q = session.createQuery(
                    "from Enrollment e " +
                            "where e.student.idcard = :sid and e.course.id = :cid " +
                            "order by e.year asc",
                    Enrollment.class);

            q.setParameter("sid", idcard);
            q.setParameter("cid", courseId);

            List<Enrollment> enrollments = q.list();

            if (enrollments.isEmpty()) {
                System.out.println("ERROR → Student " + idcard + " is not enrolled in course " + courseId);
                return;
            }

            System.out.println("Press -f, --file to write in a file text. If not, press 'p'");
            Scanner scanner = new Scanner(System.in);
            String decision = scanner.nextLine().trim();
            if (decision.equals("-f") || decision.equals("--file")) {

            }

            System.out.println("Year  Subjects                      Score");
            System.out.println("----------------------------------------------------");

            for (Enrollment enrolls : enrollments) {

                Query<Score> q2 = session.createQuery(
                        "from Score s " +
                                "where s.enrollment.id = :eid " +
                                "order by s.subject.name asc",
                        Score.class);

                q2.setParameter("eid", enrolls.getId());
                List<Score> scores = q2.list();

                for (Score s : scores) {
                    String grade = (s.getScore() == null ? "-" : s.getScore().toString());
                    System.out.printf("%-4d %-30s %s%n",
                            enrolls.getYear(),
                            s.getSubject().getName(),
                            grade);
                }
            }
        }
    }
}
