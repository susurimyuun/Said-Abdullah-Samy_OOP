import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        Product p1 = new Product("P001", "Indomie Goreng", 3500, 50, "Makanan Instan");
        Product p2 = new Product("P002", "Teh Botol Sosro", 4000, 30, "Minuman");
        Product p3 = new Product("P003", "Buku Tulis Sinar Dunia", 5000, 20, "Alat Tulis");
        Product p4 = new Product("P004", "Kopi Kapal Api", 2000, 45, "Minuman");
        Product p5 = new Product("P005", "Beras Ramos 5kg", 68000, 10, "Sembako");

        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(p3);
        manager.addProduct(p4);
        manager.addProduct(p5);

        System.out.println("=== Products Displayed ===");
        manager.displayAllProducts();
        System.out.println();

        System.out.println("=== Unique Categories ===");
        manager.displayUniqueCategories();
        System.out.println();

        System.out.println("=== Searching products ===");
        String searchTarget = "Teh Botol Sosro";
        Product found = manager.findProductByName(searchTarget);
        if (found != null) {
            System.out.print("Product found -> ");
            found.displayInfo();
        } else {
            System.out.println("Product \"" + searchTarget + "\" isn't found.");
        }
        System.out.println();

        System.out.println("=== Removing product (ID: P002) ===");
        manager.removeProduct("P002");
        manager.displayAllProducts();
        System.out.println();

        System.out.println("=== From (A-Z) ===");
        manager.displayProductsSortedByName();
        System.out.println();

        System.out.println("=== Based on Highest Price (DESCENDING) ===");
        Comparator<Product> priceDescendingComparator = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p2.getPrice(), p1.getPrice());
            }
        };

        manager.displayProductsSortedByPrice(priceDescendingComparator);
    }
}