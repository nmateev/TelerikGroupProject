package onlineshop.store;

public abstract class Product {

    private int id;
    private String name;
    private String brand;
    private String description;
    private Category categoryType;
    private int stock;
    private double price;
    private Supplier supplier;


    Product(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        setId(id);
        setName(name);
        setBrand(brand);
        setDescription(description);
        setCategoryType(categoryType);
        setStock(stock);
        setPrice(price);
        setSupplier(supplier);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategoryType() {
        return categoryType;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    /*this method serves as presenter of the store and is used to show the whole product information - name, brand,
     description, price, and stock*/
    public void showProduct() {
        StringBuilder productInfo = new StringBuilder();
        productInfo
                .append(this.getName()).append(", Brand: ").append(this.getBrand())
                .append(", Description: ").append(this.getDescription())
                .append(", Price: ").append(this.getPrice())
                .append(", Stock: ").append(this.getStock());
        System.out.println(productInfo);

    }

    /* the overridden string method presents information for the product and its purpose is to show only the name and brand
   of the product and to be called when the order of the user is completed to show the user ordered items */
    @Override
    public String toString() {
        StringBuilder currentProduct = new StringBuilder();
        currentProduct
                .append(this.getName()).append(", Brand: ").append(this.getBrand());

        return currentProduct.toString();
    }

    void setStock(int stock) {
        this.stock = stock;
    }


    private void setId(int id) {
        this.id = id;
    }


    private void setName(String name) {
        this.name = name;
    }


    private void setBrand(String brand) {
        this.brand = brand;
    }


    private void setDescription(String description) {
        this.description = description;
    }


    private void setCategoryType(Category categoryType) {
        this.categoryType = categoryType;
    }


    private void setPrice(double price) {
        this.price = price;
    }


    private void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
