package onlineshop.users;


public class VipUser extends User {

    private VipUserType type;

    public VipUser(String userName, String userPassword, VipUserType type) {
        super(userName, userPassword);
        setType(type);
    }

    public VipUser(String userName, String userPassword, String phoneNumber, String address) {
        super(userName, userPassword, phoneNumber, address);
    }

    public VipUserType getType() {
        return type;
    }

    private void setType(VipUserType type) {
        this.type = type;
    }
}