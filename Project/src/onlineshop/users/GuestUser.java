package onlineshop.users;

public class GuestUser extends User {

    private static int userIdGenerator = 123;
    private int userTempId;

    public GuestUser() {
        setUserTempId();
    }

    public int getUserTempId() {
        return userTempId;
    }

    private void setUserTempId() {
        this.userTempId = userIdGenerator;
        userIdGenerator++;
    }
}
