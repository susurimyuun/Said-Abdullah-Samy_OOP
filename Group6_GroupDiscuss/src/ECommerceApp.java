import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ECommerceApp {
    public static void main(String[] args){
        List<String> transactionHistory = new ArrayList<>();
        transactionHistory.add("TRX-001");
        transactionHistory.add("TRX-002");

        Map<String,Double> productCatalog = new HashMap<>();
        productCatalog.put("Laptop",15000000.0);
        productCatalog.put("Mouse",250000.0);
        PaymentGateway payment = new EWalletPayment("08123456789");

        System.out.println("Default Currency " + PaymentGateway.getDefaultCurrency());
        payment.processPayment(productCatalog.get("Mouse"));

        payment.printReceipt();
    }
}
