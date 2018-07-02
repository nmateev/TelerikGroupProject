package onlineshop.product;

import onlineshop.Supplier;

public class CustomProduct extends Product implements Customisable {
    private final static int COST_OF_CUSTOMISATION = 5;

    private String descriptionOfCustomisation;
    private String customisation;
    private double totalPrice;

    public CustomProduct(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier, String descriptionOfCustomisation) {
        super(id, name, brand, description, categoryType, stock, price, supplier);
        setDescriptionOfCustomisation(descriptionOfCustomisation);
    }

    @Override
    public CustomProduct addCustomisation(String customisation) {
        CustomProduct productAfterCustomisation = this;
        productAfterCustomisation.setCustomisation(customisation);
        double currentPrice = productAfterCustomisation.getPrice();
        productAfterCustomisation.setTotalPrice(currentPrice + COST_OF_CUSTOMISATION);
        return productAfterCustomisation;
    }

    @Override
    public void showProduct() {
        super.showProduct();
        StringBuilder customInfo = new StringBuilder();
        customInfo.append("This product can be customised with: ").append(this.getDescriptionOfCustomisation());
        System.out.println(customInfo);

    }

    @Override
    public String toString() {
        return super.toString() + " Customised with: " + this.getCustomisation();
    }

    public String getDescriptionOfCustomisation() {
        return descriptionOfCustomisation;
    }

    public String getCustomisation() {
        return customisation;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void setDescriptionOfCustomisation(String descriptionOfCustomisation) {
        this.descriptionOfCustomisation = descriptionOfCustomisation;
    }

    private void setCustomisation(String customisation) {
        this.customisation = customisation;
    }

    private void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
