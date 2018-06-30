package onlineshop;

public class Supplier {
    private String nameOfSupplier;

    public Supplier(String name) {
        setNameOfSupplier(name);
    }

    public String getNameOfSupplier() {
        return nameOfSupplier;
    }

    private void setNameOfSupplier(String nameOfSupplier) {
        this.nameOfSupplier = nameOfSupplier;
    }
}
