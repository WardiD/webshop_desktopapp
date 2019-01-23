package Product;

public class GeneralProduct {
    private String name;
    private int id_product;
    private String type;
    private String brand;
    private String model;
    private double price;
    private int quantity;

    public GeneralProduct(String name, int id_product, String type, String brand, String model, double price, int quantity) {
        this.name = name;
        this.id_product = id_product;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
