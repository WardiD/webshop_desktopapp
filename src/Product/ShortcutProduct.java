package Product;

public class ShortcutProduct {
    private int id_product;
    private String productName;
    private int quantity;
    private double price;

    public ShortcutProduct(int id_product, String productName, int quantity, double price) {
        this.id_product = id_product;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShortcutProduct{" +
                "id_product=" + id_product +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public int getId_product() {
        return id_product;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

}
