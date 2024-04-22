package hr.bestwebshop.bedwebshop.enums;

public enum PaymentTypeEnum {
    CashOnDelivery("Cash On Delivery"),
    PayPal("PayPal");

    private final String name;

    private PaymentTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
