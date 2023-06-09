package model;

/**
 * Class used to create the client object(having the corresponding fields in the database) with the setters and getters
 */
public class Client {
    private int id;
    private String name;
    private int age;
    private double balance;

    public Client(int id, String name, int age, double balance) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
    }
    public Client(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
