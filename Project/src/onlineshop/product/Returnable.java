package onlineshop.product;

/*Interface which when implemented adds the functionality for the store or any class that implements the interface to be returned
    to the store */
public interface Returnable {

    void increaseStockAfterReturn(int quantity, ProductDatabase database);

    void showProduct();
}
