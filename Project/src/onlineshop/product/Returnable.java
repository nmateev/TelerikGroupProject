package onlineshop.product;

public interface Returnable {

    void increaseStockAfterReturn(Product product, int quantity);

    void showProduct();
}
