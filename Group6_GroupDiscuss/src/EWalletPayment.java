class EWalletPayment implements PaymentGateway{
    private String AccountId;

    public EWalletPayment(String AccountId){
        this.AccountId = AccountId;
    }

    @Override
    public void processPayment(double amount){
        System.out.printf("Processing Rp. %.2f via E-Wallet %s%n", amount, AccountId);
    }

}
