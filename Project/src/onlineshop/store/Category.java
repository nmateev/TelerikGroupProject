package onlineshop.store;

public enum Category {
    FOODS, DRINKS, CLOTHES, SHOES, ACCESSORIES, ELECTRONICS,;

    @Override
    public String toString() {
        return this.name();
    }
}
