package com.vtschool2526.service;

import com.vtschool2526.model.Student;
import com.vtschool2526.util.HibernateSession;
import com.vtschool2526.util.StudentHandler;
import com.vtschool2526.util.XMLParser;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.List;

public class StudentService {

    public static void importStudents(String xmlFile) {

        File f = new File(xmlFile);
        if (!f.exists()) {
            System.out.println("File not found: " + xmlFile);
            System.out.println("ERROR --> Import aborted.");
            return;
        }

        try {
            SAXParser parser = XMLParser.createParser();
            StudentHandler handler = new StudentHandler();

            parser.parse(f, handler);

            List<Student> students = handler.getStudents();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            try (Session session = HibernateSession.openSession()) {
                Transaction tx = session.beginTransaction();

                for (Student s : students) {

                    Student existing = session.find(Student.class, s.getIdcard());
                    if (existing != null) {
                        System.out.println("Duplicate idcard: " + s.getIdcard());
                        System.out.println("ERROR --> Import aborted.");
                        tx.rollback();
                        return;
                    }

                    if (s.getEmail() != null && !s.getEmail().isEmpty()
                            && !isValidEmail(s.getEmail())) {
                        System.out.println("Invalid email: " + s.getEmail());
                        System.out.println("ERROR --> Import aborted.");
                        tx.rollback();
                        return;
                    }

                    if (s.getPhone() != null && !s.getPhone().isEmpty()
                            && !isValidPhone(s.getPhone())) {
                        System.out.println("Invalid phone: " + s.getPhone());
                        System.out.println("ERROR --> Import aborted.");
                        tx.rollback();
                        return;
                    }

                    session.persist(s);
                }

                tx.commit();
                System.out.println("Students imported successfully.");
            }

        } catch (Exception e) {
            System.out.println("XML FORMAT ERROR");
            System.out.println("ERROR --> Import aborted.");
        }
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static boolean isValidPhone(String phone) {
        return phone.matches("[0-9]+");
    }
}
