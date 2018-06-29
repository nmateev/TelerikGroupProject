package product;

public interface Returnable {
    int daysForPossibleReturn = 14;

    void giveMoneyBack(User user);

    void increaseStockAfterReturn(Product product, int quantity);

}
