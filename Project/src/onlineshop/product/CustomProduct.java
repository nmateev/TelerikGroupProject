package onlineshop.product;

import onlineshop.Supplier;

public class CustomProduct extends Product implements Customisable {
    final static int COST_OF_CUSTOMISATION = 5;
    String descriptionOfCustomisation;


    String customLabel;
    String customParts;
    double totalPrice;

    public CustomProduct(String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier, String descriptionOfCustomisation) {
        super(name, brand, description, categoryType, stock, price, supplier);
        setDescriptionOfCustomisation(descriptionOfCustomisation);
    }

    @Override
    public CustomProduct addLabel(String label) {
        // CustomProduct customProduct = new CustomProduct();
        //  return customProduct;
        return null;
    }

    @Override
    public CustomProduct addParts(String parts) {
        // CustomProduct customProduct = new CustomProduct();
        // return customProduct;
        return null;
    }

    @Override
    public void showProduct() {
        super.showProduct();
        StringBuilder customInfo = new StringBuilder();
        customInfo.append("This product can be customised with: ").append(this.getDescriptionOfCustomisation());
        System.out.println(customInfo);

    }

    public String getDescriptionOfCustomisation() {
        return descriptionOfCustomisation;
    }

    public String getCustomLabel() {
        return customLabel;
    }

    public String getCustomParts() {
        return customParts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void setDescriptionOfCustomisation(String descriptionOfCustomisation) {
        this.descriptionOfCustomisation = descriptionOfCustomisation;
    }

    private void setCustomLabel(String customLabel) {
        this.customLabel = customLabel;
    }

    private void setCustomParts(String customParts) {
        this.customParts = customParts;
    }

    private void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
