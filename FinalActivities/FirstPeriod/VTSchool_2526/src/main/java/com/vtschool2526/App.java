package com.vtschool2526;

import com.vtschool2526.service.EnrollmentService;
import com.vtschool2526.service.StudentService;
import com.vtschool2526.service.QualificationService;
import com.vtschool2526.service.PrintService;

public class App {

    private static final String HELP_TEXT =
            """
            Usage: vtinstitute [options]
            Options:
              -h, --help                        show this help
              -a, --add {filename.xml}          add the students in the XML file to the database.
              -e, --enroll {studentId} {courseId}   enroll a student in a course
              -p, --print {studentId} {courseId}     show the scores of a student in a course
              -q, --qualify {studentId} {courseId}   introduce the scores obtained by the student in the course.
            """;

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(HELP_TEXT);
            return;
        }

        String option = args[0];

        switch (option) {

            case "-h", "--help" -> System.out.println(HELP_TEXT);

            case "-a", "--add" -> {
                if (args.length < 2) {
                    System.out.println("ERROR --> Missing XML file.");
                    return;
                }
                StudentService.importStudents(args[1]);
            }

            case "-e", "--enroll" -> {
                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --enroll <idcard> <courseId>");
                    return;
                }
                EnrollmentService.enroll(args[1], Integer.parseInt(args[2]));
            }

            case "-p", "--print" -> {
                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --print <idcard> <courseId>");
                    return;
                }
                PrintService.print(args[1], Integer.parseInt(args[2]));
            }

            case "-q", "--qualify" -> {
                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --qualify <idcard> <courseId>");
                    return;
                }
                QualificationService.qualify(args[1], Integer.parseInt(args[2]));
            }

            default -> System.out.println("Unknown option. Use --help");
        }
    }
}
