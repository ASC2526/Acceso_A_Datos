import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String description;

    public Contact(String name, String surname, String email, String phone, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
    public String getFullName() {
        return name + " " + surname;
    }

    public String getPhone() {
        return phone;
    }

    public void show() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phone);
        System.out.println("Description: " + description);
    }
}
