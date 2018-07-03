package onlineshop.users;

public class GuestUser extends User {
    private int guestUserId;
    private static int idGenerator = 1;
    protected GuestUser() {
        super();
        setGuestUserId();
    }

    public int getGuestUserId() {
        return guestUserId;
    }

    private void setGuestUserId() {
        this.guestUserId = idGenerator;
        idGenerator++;
    }
}
