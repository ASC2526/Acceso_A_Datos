package com.vtschool2526.service;

import com.vtschool2526.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentService {

    public static int GetNumber(int num)
    {
        int num1 = num;
        return num1;
    }
    public static int GetNumber2(int num)
    {
        int num1 = num;
        return num1;
    }
    private static final SessionFactory sessionFactory =
            new Configuration().configure().buildSessionFactory();

    public static boolean importStudents(List<Student> students) {
        System.out.println("Parsed students...to be imported...:");
        Set<String> seenIds = new HashSet<>();

        boolean allValid = true;

        try (Session session = sessionFactory.openSession()) {
            for (Student s : students) {
                System.out.printf(" - %s %s (%s) phone=%s email=%s%n",
                        s.getFirstname(), s.getLastname(), s.getIdcard(),
                        s.getPhone(), s.getEmail());

                if (!seenIds.add(s.getIdcard())) {
                    System.err.println("Duplicate in XML: " + s.getIdcard());
                    allValid = false;
                    continue;
                }

                Student existing = session.find(Student.class, s.getIdcard());
                if (existing != null) {
                    System.err.println("Student already exists in DB: " + s.getIdcard());
                    allValid = false;
                    continue;
                }

                if (!isValidEmail(s.getEmail())) {
                    System.err.println("Invalid email: " + s.getEmail());
                    allValid = false;
                    continue;
                }

                if (!isValidPhone(s.getPhone())) {
                    System.err.println("Invalid phone: " + s.getPhone());
                    allValid = false;
                    continue;
                }

                session.beginTransaction();
                session.persist(s);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            System.err.println("Error importing students: " + e.getMessage());
            allValid = false;
        }

        return allValid;
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{9}");
    }
}
