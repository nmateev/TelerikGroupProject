package onlineshop.product;

import onlineshop.Supplier;

public class CustomProduct extends Product implements Customisable {

    public final int COST_OF_CUSTOMISATION = 5;

    private String descriptionOfCustomisation;
    private String customisation;

    public CustomProduct(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier, String descriptionOfCustomisation) {
        super(id, name, brand, description, categoryType, stock, price, supplier);
        setDescriptionOfCustomisation(descriptionOfCustomisation);
    }

    @Override
    public CustomProduct addCustomisation(String customisation) {

        CustomProduct productAfterCustomisation = this;
        productAfterCustomisation.setCustomisation(customisation);
        return productAfterCustomisation;
    }
    /* the overwritten string method presents information for the store and its purpose is to show only the name, brand
        and information for the custom store's customisation description and to be called when the order
        of the user is completed to show the user the items that he ordered */
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

    public int getCostOfCustomisation() {
        return COST_OF_CUSTOMISATION;
    }

    private void setDescriptionOfCustomisation(String descriptionOfCustomisation) {
        this.descriptionOfCustomisation = descriptionOfCustomisation;
    }

    private void setCustomisation(String customisation) {
        this.customisation = customisation;
    }

}
