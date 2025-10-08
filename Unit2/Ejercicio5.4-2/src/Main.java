package src;

import java.io.*;
import java.util.*;

class SortFiles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the first file: ");
        String file1 = scanner.nextLine();

        System.out.print("Enter the name of the second file: ");
        String file2 = scanner.nextLine();

        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(file1));
            String line;
            while ((line = reader1.readLine()) != null) {
                lines.add(line);
            }
            reader1.close();
        } catch (IOException e) {
            System.out.println("Error reading the first file.");
        }

        try {
            BufferedReader reader2 = new BufferedReader(new FileReader(file2));
            String line;
            while ((line = reader2.readLine()) != null) {
                lines.add(line);
            }
            reader2.close();
        } catch (IOException e) {
            System.out.println("Error reading the second file.");
        }

        bubbleSort(lines);

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("sorted.txt")));
            for (String l : lines) {
                writer.println(l);
            }
            writer.close();
            System.out.println("The file sorted.txt was created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing the file sorted.txt.");
        }

        System.out.println("\nContent of sorted.txt:");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("sorted.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading sorted.txt.");
        }
    }

    public static void bubbleSort(List<String> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    String temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
