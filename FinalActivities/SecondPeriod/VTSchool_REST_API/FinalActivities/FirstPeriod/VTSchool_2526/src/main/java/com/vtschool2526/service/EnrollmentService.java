package com.vtschool2526.service;

import com.vtschool2526.model.Course;
import com.vtschool2526.model.Enrollment;
import com.vtschool2526.model.Score;
import com.vtschool2526.model.Student;
import com.vtschool2526.model.Subject;
import com.vtschool2526.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {

    public static void enroll(String idcard, int courseId) {

        try (Session session = HibernateSession.openSession()) {

            Transaction tx = session.beginTransaction();

            Student student = session.find(Student.class, idcard);
            if (student == null) {
                System.out.println("ERROR → Student not found: " + idcard);
                return;
            }

            Course course = session.find(Course.class, courseId);
            if (course == null) {
                System.out.println("ERROR → Course not found: " + courseId);
                return;
            }

            // find last enrollment of this student in the course
            Query<Enrollment> qLast = session.createQuery(
                    "from Enrollment e " +
                            "where e.student.idcard = :sid and e.course.id = :cid " +
                            "order by e.year desc",
                    Enrollment.class
            );

            qLast.setParameter("sid", idcard);
            qLast.setParameter("cid", courseId);

            List<Enrollment> listLast = qLast.list();
            Enrollment lastEnrollment = listLast.isEmpty() ? null : listLast.get(0);

            int newYear = (lastEnrollment == null ? LocalDate.now().getYear() : lastEnrollment.getYear() + 1);

            // check if student is already enrolled in any course that year
            Query<Long> qConflict = session.createQuery(
                    "select count(e) from Enrollment e " +
                            "where e.student.idcard = :sid and e.year = :yr",
                    Long.class
            );

            qConflict.setParameter("sid", idcard);
            qConflict.setParameter("yr", newYear);

            List<Long> countList = qConflict.list();
            long conflict = countList.isEmpty() ? 0 : countList.get(0);

            if (conflict > 0) {
                System.out.println("ERROR → Student already enrolled in another course in year " + newYear);
                return;
            }

            List<Integer> subjectsToEnroll;

            if (lastEnrollment == null) {
                // if is first year, get first year subjects
                Query<Integer> q1 = session.createQuery(
                        "select sc.subject.id from SubjectCourse sc " +
                                "where sc.course.id = :cid and sc.subject.year = 1",
                        Integer.class
                );

                q1.setParameter("cid", courseId);
                subjectsToEnroll = q1.list();

                if (subjectsToEnroll.isEmpty()) {
                    System.out.println("ERROR → No first-year subjects found.");
                    return;
                }

            } else {
                // if is not first year, get the pending subjects
                String sql = "SELECT * FROM _da_vtschool_2526.subjects_pending_asc_2526(:sid, :cid)";

                @SuppressWarnings("unchecked")
                List<Integer> pending = session.createNativeQuery(sql)
                        .setParameter("sid", idcard)
                        .setParameter("cid", courseId)
                        .list();

                if (pending.isEmpty()) {
                    System.out.println("ERROR → Course already completed.");
                    return;
                }

                subjectsToEnroll = pending;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setYear(newYear);

            session.persist(enrollment);
            session.flush();

            for (Integer subId : subjectsToEnroll) {

                Subject sub = session.find(Subject.class, subId);

                Score s = new Score();
                s.setEnrollment(enrollment);
                s.setSubject(sub);
                s.setScore(null);

                session.persist(s);
            }

            tx.commit();
            System.out.println("Enrollment completed successfully for year " + newYear);

        } catch (Exception e) {
            System.out.println("ERROR → Enrollment failed: " + e.getMessage());
        }
    }
}
