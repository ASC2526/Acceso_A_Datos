package com.vtschool2526.util;

import com.vtschool2526.model.Student;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler extends DefaultHandler {

    private List<Student> students = new ArrayList<>();
    private Student currentStudent = null;
    private StringBuilder content = new StringBuilder();

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("student")) {
            currentStudent = new Student();
        }
        content.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (currentStudent != null) {
            switch (qName.toLowerCase()) {
                case "idcard" -> currentStudent.setIdcard(content.toString().trim());
                case "firstname" -> currentStudent.setFirstname(content.toString().trim());
                case "lastname" -> currentStudent.setLastname(content.toString().trim());
                case "phone" -> currentStudent.setPhone(content.toString().trim());
                case "email" -> currentStudent.setEmail(content.toString().trim());
                case "student" -> students.add(currentStudent);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content.append(ch, start, length);
    }
}
