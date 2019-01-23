package Product;

public class GeneralProduct {
    private String name;
    private int id_product;
    private String type;
    private String brand;
    private String model;
    private double price;
    private int quantity;

    public GeneralProduct(String name, int id_product, String type, double price, int quantity) {
        this.name = name;
        this.id_product = id_product;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "GeneralProduct{" +
                "name='" + name + '\'' +
                ", id_product=" + id_product +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // Getters
    public String getName() {
        return name;
    }

    public int getId_product() {
        return id_product;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
