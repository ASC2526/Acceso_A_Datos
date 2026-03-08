package com.vtschool2526.service;

import com.vtschool2526.model.Student;
import com.vtschool2526.model.api.ApiManager;
import com.vtschool2526.util.StudentHandler;
import com.vtschool2526.util.XMLParser;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.List;

public class StudentService {

    public static void importStudents(String xmlFile) {

        File f = new File("src/main/java/com/vtschool2526/fpfa_tests/" + xmlFile);

        if (!f.exists()) {
            System.out.println("File not found.");
            return;
        }

        List<Student> students;

        try {
            SAXParser parser = XMLParser.createParser();
            StudentHandler handler = new StudentHandler();
            parser.parse(f, handler);
            students = handler.getStudents();
        } catch (Exception e) {
            System.out.println("API ERROR:");
            System.out.println(e.getMessage());
            System.out.println("ERROR --> Import aborted.");
            return;
        }

        try {
            ApiManager apiManager = new ApiManager();
            apiManager.createStudents(students);
            System.out.println("Students imported successfully.");
        } catch (Exception e) {
            System.out.println("API ERROR:");
            System.out.println(e.getMessage());
            System.out.println("ERROR --> Import aborted.");
        }
    }
}