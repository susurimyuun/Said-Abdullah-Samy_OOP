public class Product implements Taggable, Sellable, Comparable<Product> {
    private String productID;
    private String name;
    private int price;
    private int quantity;
    private String category;

    public Product(String productID, String name, int price, int quantity, String category) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Getter methods
    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String getCategory() {
        return category;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(Product other) {
        return this.name.compareToIgnoreCase(other.getName());
    }
}