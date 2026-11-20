import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryManager {
    private Map<String, Product> inventory;

    private Set<String> uniqueCategories;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        this.uniqueCategories = new HashSet<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.getProductID(), product);
        uniqueCategories.add(product.getCategory());
    }

    public void removeProduct(String productID) {
        inventory.remove(productID);
    }

    public Product findProductByName(String name) {
        for (Product product : inventory.values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventaris kosong.");
            return;
        }
        for (Product product : inventory.values()) {
            product.displayInfo();
        }
    }

    public void displayUniqueCategories() {
        System.out.println("Kategori Produk Unik:");
        for (String category : uniqueCategories) {
            System.out.println("- " + category);
        }
    }

    public void displayProductsSortedByName() {
        List<Product> productList = new ArrayList<>(inventory.values());
        Collections.sort(productList);
        for (Product product : productList) {
            product.displayInfo();
        }
    }

    public void displayProductsSortedByPrice(Comparator<Product> comparator) {
        List<Product> productList = new ArrayList<>(inventory.values());
        productList.sort(comparator);
        for (Product product : productList) {
            product.displayInfo();
        }
    }
}