import java.io.*;
import java.util.Scanner;

class ShowFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file to display: ");
        String fileName = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int count = 0;

            System.out.println("\nShowing file content:\n");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                count++;

                if (count == 23) {
                    System.out.println("\nPress Enter to continue");
                    scanner.nextLine();
                    count = 0;
                }
            }

            reader.close();
            System.out.println("\nEnd of file.");
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
    }
}
