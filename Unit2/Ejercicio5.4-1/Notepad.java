import java.io.*;
import java.util.Scanner;

public class Notepad {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "notepad.txt";
        boolean append = false;

        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("El archivo ya existe. Si desea sobrescribirlo pulse 'S' y si desea agregarlo al final pulse 'A'.");
            String choice = scanner.nextLine();
            if (choice.equals("A") || choice.equals("a")) {
                append = true;
            } else if (choice.equals("S") || choice.equals("s")) {
                append = false;
            } else {
                System.out.println("Opción no válida, se sobrescribirá por defecto.");
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

            System.out.println("Escriba 'SALIR' para terminar.");

            while (true) {
                System.out.print(lineNumber + ": ");
                String line = scanner.nextLine();
                if (line.equals("SALIR") || line.equals("salir")) {
                    break;
                }
                writer.println(lineNumber + ": " + line);
                lineNumber++;
            }

            System.out.println("Archivo guardado correctamente");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo.");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
