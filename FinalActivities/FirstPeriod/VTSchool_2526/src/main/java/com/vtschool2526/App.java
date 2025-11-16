package com.vtschool2526;

import com.vtschool2526.service.StudentService;
import com.vtschool2526.service.EnrollmentService;

public class App {

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
