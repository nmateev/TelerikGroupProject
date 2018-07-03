package onlineshop.product;

public interface Returnable {

    void increaseStockAfterReturn(int quantity, ProductDatabase database);

    void showProduct();
}
