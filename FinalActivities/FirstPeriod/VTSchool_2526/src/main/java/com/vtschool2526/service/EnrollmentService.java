package com.vtschool2526.service;

import com.vtschool2526.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class EnrollmentService {

    private static SessionFactory sessionFactory = null;

    private static Session openSession() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static void enroll(String studentId, int courseId, int year) {

        try (Session session = openSession()) {

            Transaction trans = session.beginTransaction();

            Student student = session.find(Student.class, studentId);
            if (student == null) {
                System.out.println("ERROR --> Student not found.");
                return;
            }
            Course course = session.find(Course.class, courseId);
            if (course == null) {
                System.out.println("ERROR --> Course not found.");
                return;
            }

            Query<Long> q = session.createQuery(
                    "select count(e) from Enrollment e where e.student.idcard = :sid and e.year = :yr",
                    Long.class);
            q.setParameter("sid", studentId);
            q.setParameter("yr", year);

            if (q.uniqueResult() > 0) {
                System.out.println("ERROR --> Student already enrolled this year.");
                return;
            }

            String sql = "SELECT * FROM _da_vtschool_2526.subjects_not_passed_asc_2526(:sid, :cid)";

            List<Integer> subjects =
                    session.createNativeQuery(sql).setParameter("sid", studentId)
                            .setParameter("cid", courseId)
                            .getResultList();

            if (subjects.isEmpty()) {
                System.out.println("ERROR --> Course already completed.");
                return;
            }

            Enrollment e = new Enrollment();
            e.setStudent(student);
            e.setCourse(course);
            e.setYear(year);

            session.persist(e);
            session.flush();

            for (Integer sid : subjects) {
                Subject subj = session.find(Subject.class, sid);
                Score sc = new Score();
                sc.setEnrollment(e);
                sc.setSubject(subj);
                sc.setScore(null);
                session.persist(sc);
            }

            trans.commit();
            System.out.println("Enrollment completed successfully.");

        } catch (Exception ex) {
            System.out.println("Error enrolling student --> " + ex.getMessage());
        }
    }
}
