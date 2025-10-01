import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String filename;

        InputStream myStream = null;

        Scanner myScanner = new Scanner(System.in);
        System.out.print("Enter the image filename: ");
        myScanner.nextLine();
        filename = myScanner.nextLine();
        try {
            myStream = new FileInputStream(filename);
        }
        catch (Exception e) {
            System.out.println("Error opening file: " + e.getMessage());
        }

        byte myDescriptor[] = new byte[6];
        int bytesRead = myStream.read(myDescriptor);

        if (bytesRead != 6){
            System.out.println("Error reading file");
        }
        else {
            if (myDescriptor[0] == (byte) 0x42 && myDescriptor[1] == (byte) 0x4D) {
                System.out.println("BMP file format detected");
            }
            else if  (myDescriptor[0] == (byte) 0x47 && myDescriptor[1] == (byte) 0x61 &&
                      myDescriptor[2] == (byte) 0x46 && myDescriptor[3] == (byte) 0x38 &&
                     (myDescriptor[4] == (byte) 0x37 || myDescriptor[4] == (byte) 0x39)&&
                      myDescriptor[5] == (byte) 0x61) {
                System.out.println("GIF file format detected");
            }
            else if  (myDescriptor[0] == (byte) 0x00 && myDescriptor[1] == (byte) 0x00 &&
                      myDescriptor[2] == (byte) 0x01 && myDescriptor[3] == (byte) 0x00) {
                System.out.println("ICO file format detected");
            }
            else if (myDescriptor[0] == (byte) 0xFF && myDescriptor[1] == (byte) 0xD8 &&
            myDescriptor[2] == (byte) 0xFF) {
                System.out.println("JPEG file format detected");
            }
            else if (myDescriptor[0] == (byte) 0x89 && myDescriptor[1] == (byte) 0x50 &&
                        myDescriptor[2] == (byte) 0x4E && myDescriptor[3] == (byte) 0x47) {
                System.out.println("PNG file format detected");
            }
            else System.out.println("Unknown file format");
        }
    }
}