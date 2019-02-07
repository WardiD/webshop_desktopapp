package Product;

/**
 * Represents short description of products
 */
public class ShortcutProduct {
    private int id_product;
    private String productName;
    private int quantity;
    private double price;

    /**
     * Creates shortcut of product
     * @param id_product ID of product in database
     * @param productName product name
     * @param quantity quantity of product in store
     * @param price price of one product
     */
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
