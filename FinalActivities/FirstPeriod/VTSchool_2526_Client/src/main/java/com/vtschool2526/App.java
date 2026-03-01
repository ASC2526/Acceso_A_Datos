package com.vtschool2526;

import com.vtschool2526.service.*;

public class App {

    private static final String HELP_TEXT =
            """
            Usage: vtinstitute [options]
            Options:
              -h, --help                               show this help
              -a, --add {filename.xml}                 add the students in the XML file to the database.
              -e, --enroll {studentId} {courseId}      enroll a student in a course
              -q, --qualify {studentId} {courseId}     introduce scores for a student
              -p, --print {studentId} {courseId}       show student scores in a course
              -d, --diploma {studentId} {courseId}     show enrollment diploma
            """;

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(HELP_TEXT);
            return;
        }

        String option = args[0];

        switch (option) {

            case "-h", "--help" ->
                    System.out.println(HELP_TEXT);

            // import students
            case "-a", "--add" -> {

                if (args.length < 2) {
                    System.out.println("ERROR --> Missing XML file.");
                    return;
                }

                StudentService.importStudents(args[1]);
            }

            // enroll student
            case "-e", "--enroll" -> {

                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --enroll <studentId> <courseId>");
                    return;
                }

                EnrollmentService.enroll(
                        args[1],
                        Integer.parseInt(args[2])
                );
            }

            // qualify student
            case "-q", "--qualify" -> {

                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --qualify <studentId> <courseId>");
                    return;
                }

                QualificationService.qualify(
                        args[1],
                        Integer.parseInt(args[2])
                );
            }

            // print scores
            case "-p", "--print" -> {

                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --print <studentId> <courseId>");
                    return;
                }

                PrintService.print(
                        args[1],
                        Integer.parseInt(args[2])
                );
            }

              // diploma
//            case "-d", "--diploma" -> {
//
//                if (args.length < 3) {
//                    System.out.println("ERROR --> Usage: --diploma <studentId> <courseId>");
//                    return;
//                }
//
//                DiplomaService.showDiploma(
//                        args[1],
//                        Integer.parseInt(args[2])
//                );
//            }

            default ->
                    System.out.println("Unknown option. Use --help");
        }
    }
}