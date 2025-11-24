package com.vtschool2526.service;

import com.vtschool2526.model.Score;
import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.Student;
import com.vtschool2526.model.Course;
import com.vtschool2526.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class QualificationService {

    public static void qualify(String idcard, int courseId) {

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

            // get latest enrollment
            Query<Enrollment> q = session.createQuery(
                    "from Enrollment e where e.student.idcard = :sid and e.course.id = :cid order by e.year desc",
                    Enrollment.class);

            q.setParameter("sid", idcard);
            q.setParameter("cid", courseId);

            List<Enrollment> list = q.list();
            Enrollment enr = list.isEmpty() ? null : list.get(0);

            if (enr == null) {
                System.out.println("ERROR → Student " + idcard + " is not enrolled in course " + courseId);
                return;
            }

            // get scores with null value
            Query<Score> q2 = session.createQuery(
                    "from Score s where s.enrollment.id = :eid and s.score is null order by s.subject.name",
                    Score.class);

            q2.setParameter("eid", enr.getId());

            List<Score> scores = q2.list();

            if (scores.isEmpty()) {
                System.out.println("There are no pending subjects to qualify.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            Transaction tx = session.beginTransaction();

            // ask for each pending subject
            for (Score s : scores) {
                while (true) {
                    System.out.print("Grade for " + s.getSubject().getName() + " (0-10 or 99 to skip): ");
                    String input = sc.nextLine().trim();

                    try {
                        int grade = Integer.parseInt(input);

                        if (grade == 99) {
                            break;
                        }

                        if (grade < 0 || grade > 10) {
                            System.out.println("Invalid grade. Must be 0-10 or 99.");
                            continue;
                        }

                        s.setScore(grade);
                        session.merge(s);
                        break;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
            }

            tx.commit();
            System.out.println("Grades updated successfully.");
        }
    }
}
