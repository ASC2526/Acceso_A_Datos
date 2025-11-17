package com.vtschool2526.service;

import com.vtschool2526.model.Student;
import com.vtschool2526.util.StudentHandler;
import com.vtschool2526.util.XMLParser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.List;

public class StudentService {

    private static SessionFactory sessionFactory = null;

    private static final String IMPORT_ABORTED = "ERROR --> Import aborted. No students have been saved.";

    private static Session openSession() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static void importStudents(String xmlFile) {

        try {
            SAXParser parser = XMLParser.createParser();
            StudentHandler handler = new StudentHandler();

            parser.parse(new File(xmlFile), handler);

            List<Student> students = handler.getStudents();

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
                        System.out.println(IMPORT_ABORTED);
                        return;
                    }

                    if (s.getEmail() != null && !isValidEmail(s.getEmail())) {
                        System.out.println("Invalid email for student: " + s.getIdcard());
                        System.out.println(IMPORT_ABORTED);
                        return;
                    }

                    if (s.getPhone() != null && !isValidPhone(s.getPhone())) {
                        System.out.println("Invalid phone for student: " + s.getIdcard());
                        System.out.println(IMPORT_ABORTED);
                        return;
                    }
                    session.persist(s);
                }

                tx.commit();
                System.out.println("Students imported successfully.");
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
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }
}
