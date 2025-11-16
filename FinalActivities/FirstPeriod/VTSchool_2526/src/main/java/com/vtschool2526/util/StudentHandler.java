package com.vtschool2526.util;

import com.vtschool2526.model.Student;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class StudentHandler {

    public static List<Student> parseStudents(Document doc) {

        List<Student> list = new ArrayList<>();

        NodeList nl = doc.getElementsByTagName("student");

        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            Student s = new Student();

            s.setIdcard(e.getElementsByTagName("idcard").item(0).getTextContent());
            s.setFirstname(e.getElementsByTagName("firstname").item(0).getTextContent());
            s.setLastname(e.getElementsByTagName("lastname").item(0).getTextContent());

            Node emailNode = e.getElementsByTagName("email").item(0);
            if (emailNode != null) {
                s.setEmail(emailNode.getTextContent());
            }

            Node phoneNode = e.getElementsByTagName("phone").item(0);
            if (phoneNode != null) {
                s.setPhone(phoneNode.getTextContent());
            }

            list.add(s);
        }

        return list;
    }
}
