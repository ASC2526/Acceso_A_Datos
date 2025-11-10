package com.vtschool2526.util;

import com.vtschool2526.model.Student;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class XMLParser {

    public static List<Student> parseStudents(File xmlFile) throws Exception {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        StudentHandler handler = new StudentHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(new InputSource(new FileReader(xmlFile)));
        return handler.getStudents();
    }
}
