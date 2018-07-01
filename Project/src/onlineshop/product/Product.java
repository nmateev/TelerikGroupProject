package onlineshop.product;

import onlineshop.Supplier;

public abstract class Product {
    static int idGenerator = 1;
    private int id;
    private String name;
    private String brand;
    private String description;
    private Category categoryType;
    private int stock;
    private double price;
    private Supplier supplier;


    Product(String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        setId(idGenerator);
        setName(name);
        setBrand(brand);
        setDescription(description);
        setCategoryType(categoryType);
        setStock(stock);
        setPrice(price);
        setSupplier(supplier);
        idGenerator++;
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

    public void showProduct() {
        StringBuilder productInfo = new StringBuilder();
        productInfo
                .append(this.getName()).append(", Brand: ").append(this.getBrand())
                .append(", Description: ").append(this.getDescription())
                .append(", Price: ").append(this.getPrice())
                .append(", Stock: ").append(this.getStock());
        System.out.println(productInfo);

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


    private void setStock(int stock) {
        this.stock = stock;
    }


    private void setPrice(double price) {
        this.price = price;
    }


    private void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
