package onlineshop.users;


public class RegularUser extends User {

    public RegularUser(String userName, String userPassword) {
        super(userName, userPassword);
    }

    public RegularUser(String userName, String userPassword, String phoneNumber, String address) {
        super(userName, userPassword, phoneNumber, address);
    }

}
