package onlineshop.users;


public abstract class User extends Cart {
    private String userName;
    private String phone;
    private String address;



    public abstract void giveOrder();
}