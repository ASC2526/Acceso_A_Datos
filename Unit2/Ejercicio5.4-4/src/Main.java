import java.io.*;
import java.util.Scanner;

class SearchText {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the file: ");
        String fileName = sc.nextLine();

        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("The file " + fileName + " does not exist.");
            return;
        }

        System.out.println("File found.");
        System.out.print("Enter the word to search: ");
        String word = sc.nextLine();

        try (BufferedReader reader1 = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNumber = 0;
            boolean found = false;

            while ((line = reader1.readLine()) != null) {
                lineNumber++;
                if (line.contains(word)) {
                    System.out.println("Line " + lineNumber + ": " + line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("The word was not found in the file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
