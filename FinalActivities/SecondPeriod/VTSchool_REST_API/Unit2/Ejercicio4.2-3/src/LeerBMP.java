import java.io.FileInputStream;
import java.io.IOException;

public class LeerBMP {
    public static void main(String[] args) {
        String filename = "imagen.bmp"; // Cambia por tu archivo BMP

        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] header = new byte[54]; // El header estándar son 54 bytes
            int bytesRead = fis.read(header);

            if (bytesRead < 54) {
                System.out.println("El archivo es demasiado pequeño para ser un BMP válido.");
                return;
            }

            // Firma "BM" (0x42 0x4D)
            if (header[0] != 'B' || header[1] != 'M') {
                System.out.println("El archivo no es un BMP válido.");
                return;
            }

            // Leer datos del header
            int fileSize = toIntLE(header, 2);
            int width = toIntLE(header, 18);
            int height = toIntLE(header, 22);
            int bitsPerPixel = toShortLE(header, 28);

            // Mostrar resultados
            System.out.println("Tamaño del archivo: " + fileSize + " bytes");
            System.out.println("Ancho: " + width + " px");
            System.out.println("Alto: " + height + " px");
            System.out.println("Bits por píxel: " + bitsPerPixel);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Convierte 4 bytes en entero
    private static int toIntLE(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) |
                ((bytes[offset + 1] & 0xFF) << 8) |
                ((bytes[offset + 2] & 0xFF) << 16) |
                ((bytes[offset + 3] & 0xFF) << 24);
    }

    // Convierte 2 bytes en short
    private static int toShortLE(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) |
                ((bytes[offset + 1] & 0xFF) << 8);
    }
}

