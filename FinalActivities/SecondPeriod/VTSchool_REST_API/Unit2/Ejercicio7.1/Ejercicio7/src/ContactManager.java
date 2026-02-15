import java.io.*;
import java.util.*;

public class ContactManager {
    private static final String FILE_NAME = "contacts.obj";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Contact> contacts = new ArrayList<Contact>();

        contacts = loadContacts();

        int option = 0;
        while (option != 4) {
            System.out.println("\n CONTACT MAIN");
            System.out.println("1. Add new contact");
            System.out.println("2. Show all contacts");
            System.out.println("3. Search contact");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();

                    Contact c = new Contact(name, surname, email, phone, description);
                    contacts.add(c);
                    saveContacts(contacts);
                    System.out.println("Contact saved correctly");
                    break;

                case 2:
                    if (contacts.isEmpty()) {
                        System.out.println("There are no saved contacts");
                    } else {
                        for (Contact contact : contacts) {
                            contact.show();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Introduce name and surname or phone number: ");
                    String search = scanner.nextLine();
                    boolean found = false;

                    for (Contact contact : contacts) {
                        if (contact.getFullName().equals(search) || contact.getPhone().equals(search)) {
                            contact.show();
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("No contact was found with that information.\n");
                    }
                    break;

                case 4:
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Not valid option");
                    break;
            }
        }
    }

    public static void saveContacts(List<Contact> contacts) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(contacts);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving the contacts");
        }
    }

    public static List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
                contacts = (List<Contact>) input.readObject();
                input.close();
            } catch (IOException e) {
                System.out.println("Error reading the contact files");
            } catch (ClassNotFoundException e) {
                System.out.println("Error: Contact class not founded");
            }
        }

        return contacts;
    }
}
