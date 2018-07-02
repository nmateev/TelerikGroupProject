package onlineshop.product;

public enum Category {
    FOODS, DRINKS, CLOTHES, SHOES, ACCESSORIES, ELECTRONICS,;

    @Override
    public String toString() {
        return this.name();
    }
}
