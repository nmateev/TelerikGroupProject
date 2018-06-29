package product;

public class CustomProduct extends Product implements Customisable {
    final static int COST_OF_CUSTOMISATION = 5;
    String descriptionOfCustomisation;
    String customLabel;
    String customParts;
    double totalPrice;

    @Override
    public CustomProduct addLabel(String label) {

    }

    @Override
    public CustomProduct addParts(String parts) {

    }
}
