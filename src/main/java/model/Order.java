package model;

public class Order {
    private int id;
    private int idClient;
    private int idProduct;

    public Order(int id, int idClient, int idProduct) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
    }
    public Order(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
