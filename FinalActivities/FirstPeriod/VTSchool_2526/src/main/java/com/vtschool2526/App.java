package com.vtschool2526;

import com.vtschool2526.service.StudentService;
import com.vtschool2526.service.EnrollmentService;

public class App {

    private static final String HELP_TEXT =
            """
                    VT School - command line
                    Usage:
                      java -jar VTSchool2526.jar [option] [args]
                    
                    Options:
                      -h, --help            Show help text
                      -a, --add <file>      Import students from XML file
                      -e, --enroll ...      (pending...)
                      -q, --qualify ...     (pending...)
                      -p, --print ...       (pending...)
                    """;

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(HELP_TEXT);
            return;
        }

        switch (args[0]) {

            case "--help":
            case "-h":
                System.out.println(HELP_TEXT);
                break;

            case "--add":
            case "-a":
                if (args.length < 2) {
                    System.out.println("Usage: --add <xmlfile>");
                    return;
                }
                StudentService.importStudents(args[1]);
                break;

            case "--enroll":
            case "-e":
                if (args.length < 4) {
                    System.out.println("Usage: --enroll <idcard> <courseId> <year>");
                    return;
                }
                try {
                    int course = Integer.parseInt(args[2]);
                    int year = Integer.parseInt(args[3]);
                    EnrollmentService.enroll(args[1], course, year);
                } catch (NumberFormatException e) {
                    System.out.println("Error --> courseId and year must be numbers");
                }
                EnrollmentService.enroll(
                        args[1],
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3])
                );
                break;

            default:
                System.out.println("Unknown option.\n");
                System.out.println(HELP_TEXT);
        }

    }
}
