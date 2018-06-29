package onlineshop.orders;

public class Cart {

    public void add(Product product) {
        userProducts.add(product);
    }

    public void delete(String productName) {
        if (userProducts.contains(productName)) {
            userProducts.remove(productName);
        }
    }

    public int count(){
        return userProducts.size();
    }
}
