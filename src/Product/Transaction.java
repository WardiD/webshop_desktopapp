package Product;

import java.util.Date;

public class Transaction {
    private int id;
    private String workerName;
    private String status;
    private Date placedDate;
    private Date realizationDate;
    private double price;
    private int id_cart;

    public Transaction(int id, String workerName, String status, Date placedDate, Date realizationDate, double price, int id_cart) {
        this.id = id;
        this.workerName = workerName;
        this.status = status;
        this.placedDate = placedDate;
        this.realizationDate = realizationDate;
        this.price = price;
        this.id_cart = id_cart;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", workerName='" + workerName + '\'' +
                ", status='" + status + '\'' +
                ", placedDate=" + placedDate +
                ", realizationDate=" + realizationDate +
                ", price=" + price +
                ", id_cart=" + id_cart +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getStatus() {
        return status;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public Date getRealizationDate() {
        return realizationDate;
    }

    public double getPrice() {
        return price;
    }

    public int getId_cart() {
        return id_cart;
    }
}


