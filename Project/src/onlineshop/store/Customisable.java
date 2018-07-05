package onlineshop.store;

//Interface that when implemented will add the possibility for a product to be customised
public interface Customisable {

    CustomProduct addCustomisation(String customisation);

    void showProduct();
}
