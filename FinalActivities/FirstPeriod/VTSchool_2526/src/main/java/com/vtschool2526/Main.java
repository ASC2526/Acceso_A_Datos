package com.vtschool2526;

import com.vtschool2526.model.Student;
import com.vtschool2526.service.StudentService;
import com.vtschool2526.util.XMLParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List; // h

public class Main {

    private static final String HELP_TEXT =
            "VT School - command line\n" +
                    "Usage:\n" +
                    "  java -jar VTSchool2526.jar [option] [args]\n\n" +
                    "Options:\n" +
                    "  -h, --help            Show help text\n" +
                    "  -a, --add <file>      Import students from XML file\n" +
                    "  -e, --enroll ...      (pending...)\n" +
                    "  -q, --qualify ...     (pending...)\n" +
                    "  -p, --print ...       (pending...)\n";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(HELP_TEXT);
            return;
        }

        String opt = args[0].trim();

        try {
            switch (opt) {
                case "-h":
                case "--help":
                    System.out.println(HELP_TEXT);
                    break;

                case "-a":
                case "--add":
                    if (args.length < 2) {
                        System.err.println("Error: you must provide an XML file path for --add");
                        break;
                    }
                    Path xmlPath = Path.of(args[1]);
                    if (!Files.exists(xmlPath)) {
                        System.err.println("Error: file not found -> " + xmlPath);
                        break;
                    }

                    List<Student> students = XMLParser.parseStudents(xmlPath.toFile());
                    boolean ok = StudentService.importStudents(students);
                    if (ok) {
                        System.out.println("All students validated correctly!");
                    } else {
                        System.out.println("Import aborted due to validation errors.");
                    }
                    break;

                default:
                    System.err.println("Unknown option: " + opt);
                    System.out.println("Use --help for usage.");
                    break;
            }
        } catch (Exception ex) {
            System.err.println("Unhandled error: " + ex.getMessage());
        }
    }
}
