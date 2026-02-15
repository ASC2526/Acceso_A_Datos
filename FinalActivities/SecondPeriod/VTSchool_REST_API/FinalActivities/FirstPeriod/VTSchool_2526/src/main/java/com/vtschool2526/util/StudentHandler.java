package com.vtschool2526.util;

import com.vtschool2526.model.Student;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentHandler extends DefaultHandler {

    private final List<Student> students = new ArrayList<>();
    private Student current = null;
    private String tagContent = null;

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("student")) {
            current = new Student();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        tagContent = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (current == null) return;

        if (qName.equalsIgnoreCase("idcard")) {
            current.setIdcard(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("firstname")) {
            current.setFirstname(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("lastname")) {
            current.setLastname(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("email")) {
            current.setEmail(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("phone")) {
            current.setPhone(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("student")) {
            students.add(current);
            current = null;
        }
        else if (qName.equalsIgnoreCase("address")) {
            current.setAddress(tagContent.trim());
        }
    }
}
