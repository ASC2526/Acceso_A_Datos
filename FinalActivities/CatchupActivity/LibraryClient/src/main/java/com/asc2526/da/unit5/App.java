package com.asc2526.da.unit5;

import com.asc2526.da.unit5.service.BookService;
import com.asc2526.da.unit5.service.LendingService;

public class App {

    private static final String HELP_TEXT =
            """
            Usage: library client [options]
            Options:
              -h, --help                          show this help
              -a, --add <file.xml>                add books from XML file
              -l, --lend <isbn> <user_code>       lend a book to a user
              -r, --return <isbn> <user_code>     return a book
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

            case "-a", "--add" -> {
                if (args.length < 2) {
                    System.out.println("ERROR: Missing XML file.");
                    return;
                }
                BookService.importBooks(args[1]);
            }

            case "-l", "--lend" -> {
                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --lend <isbn> <user_code>");
                    return;
                }
                LendingService.lend(args[1], args[2]);
            }

            case "-r", "--return" -> {
                if (args.length < 3) {
                    System.out.println("ERROR --> Usage: --return <isbn> <user_code>");
                    return;
                }
                LendingService.returnBook(args[1], args[2]);
            }

            default ->
                    System.out.println("Unknown option. Use --help");
        }
    }
}