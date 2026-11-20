interface PaymentGateway {
    //Abstract Method
    void processPayment(double amount);


    //Default Method
    default void printReceipt() {
        System.out.println("Print Invoice Payment...");
    }

    //Static Method
    static String getDefaultCurrency() {
        return "IDR";
    }
}