package model;

public class Client {
    private final int id;
    private String firstName;
    private String lastName;

    public Client(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
