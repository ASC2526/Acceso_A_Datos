import java.io.*;
import java.util.Scanner;

public class Notepad {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name (example: notes.txt): ");
        String fileName = scanner.nextLine();

        boolean append = false;
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("The file already exists. Press 'S' to overwrite or 'A' to append.");
            String choice = scanner.nextLine();
            if (choice.equals("A") || choice.equals("a")) {
                append = true;
            } else if (choice.equals("S") || choice.equals("s")) {
                append = false;
            } else {
                System.out.println("Invalid option, file will be overwritten by default.");
                append = false;
            }
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
            int lineNumber = 1;

            if (append) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    lineNumber++;
                }
                reader.close();
            }

            System.out.println("Type your text, write 'EXIT' to finish:");

            String line = "";

            while (!(line.equals("EXIT") || line.equals("exit"))) {
                System.out.print(lineNumber + ": ");
                line = scanner.nextLine();

                if (!(line.equals("EXIT") || line.equals("exit"))) {
                    writer.println(lineNumber + ": " + line);
                    lineNumber++;
                }
            }

            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("An error happened while writing the file.");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
