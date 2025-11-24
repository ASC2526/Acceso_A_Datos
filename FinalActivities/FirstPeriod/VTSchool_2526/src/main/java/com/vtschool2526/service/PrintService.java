package com.vtschool2526.service;

import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.Score;
import com.vtschool2526.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PrintService {

    public static void print(String idcard, int courseId) {

        try (Session session = HibernateSession.openSession()) {
            // get enrollments of this student in this course
            Query<Enrollment> q = session.createQuery(
                    "from Enrollment e " +
                            "where e.student.idcard = :sid and e.course.id = :cid " +
                            "order by e.year asc",
                    Enrollment.class);

            q.setParameter("sid", idcard);
            q.setParameter("cid", courseId);

            List<Enrollment> enrollments = q.list();

            if (enrollments.isEmpty()) {
                System.out.println("ERROR --> No enrollment found.");
                return;
            }

            System.out.println("Year  Subjects                      Score");
            System.out.println("----------------------------------------------------");

            // print scores and subjects
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
                    System.out.printf(
                            "%-4d %-30s %s%n",
                            enrolls.getYear(),
                            s.getSubject().getName(),
                            grade
                    );
                }
            }
        }
    }
}
