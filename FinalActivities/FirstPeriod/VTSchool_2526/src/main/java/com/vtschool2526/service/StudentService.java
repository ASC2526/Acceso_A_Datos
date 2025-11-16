package com.vtschool2526.service;

import com.vtschool2526.model.Student;
import com.vtschool2526.util.XMLParser;
import com.vtschool2526.util.StudentHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentService {

    private static SessionFactory sessionFactory = null;

    private static Session openSession() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static void importStudents(String xmlFile) {

        try {
            List<Student> students =
                    StudentHandler.parseStudents(XMLParser.parse(xmlFile));

            if (students.isEmpty()) {
                System.out.println("No students found in XML.");
                return;
            }

            try (Session session = openSession()) {
                Transaction tx = session.beginTransaction();

                for (Student s : students) {

                    Student existing = session.find(Student.class, s.getIdcard());
                    if (existing != null) {
                        System.out.println("Student already exists: " + s.getIdcard());
                        continue;
                    }
                    if (s.getEmail() != null && !isValidEmail(s.getEmail())) {
                        System.out.println("Invalid email for: " + s.getIdcard());
                        continue;
                    }
                    if (s.getPhone() != null && !isValidPhone(s.getPhone())) {
                        System.out.println("Invalid phone for: " + s.getIdcard());
                        continue;
                    }

                    session.persist(s);
                }

                tx.commit();
                System.out.println("Students imported.");
            }

        } catch (Exception e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }
    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static boolean isValidPhone(String phone) {

        if (phone == null || phone.isEmpty()) {
            return false;
        }

        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
