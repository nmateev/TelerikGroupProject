import onlineshop.product.ProductDatabase;

public class Application {
    public static void main(String[] args) {
        ProductDatabase database = new ProductDatabase();
        database.loadProductDatabase();
        database.getProducts().stream().forEach(product -> product.showProduct());
    }
}
