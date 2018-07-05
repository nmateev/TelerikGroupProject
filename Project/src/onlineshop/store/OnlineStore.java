package onlineshop.store;

import onlineshop.shipping.*;
import onlineshop.users.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class OnlineStore {
    private static final String MESSAGE_FOR_ORDER_PROCESSING = "Please wait. Your order is being processed...\n";
    private static final String MESSAGE_FOR_ORDER_COMPLETED = "Order completed. You can see your order summary in the pop-up window!\n";
    private static final String MESSAGE_FOR_ENCOURAGING_SHOPPING_AGAIN = "\nThank you for your order. We are looking forward to hearing back from you. \n";
    private static final String MESSAGE_FOR_COMPLETED_RETURN_OF_PRODUCT = "Product successfully returned.";
    private static final String MESSAGE_FOR_SUCCESSFUL_LOGIN = "You are successfully logged in as: ";
    private static final String MESSAGE_FOR_UNSUCCESSFUL_LOGIN = "Your login credentials are not correct. You are logged in as guest user";
    private static final String SIGNED_AS_GUEST_USER_MESSAGE = "You are logged in as guest user";
    private static final String MESSAGE_FOR_IMPOSSIBLE_RETURN = "You cannot return this product";
    private static final String MESSAGE_FOR_NOT_ENOUGH_STOCK = "Not enough stock.\nDo you want to buy only %d units of this product: %s?\nIf no you will cancel the order\nType in yes or no\n";


    private static final double DISCOUNT_FOR_SILVER_USER = 0.05;
    private static final double DISCOUNT_FOR_GOLD_USER = 0.10;
    private static final double DISCOUNT_FOR_PLATINUM_USER = 0.20;

    private ArrayList<Order> storeOrderList;
    private ProductDatabase database;
    private ArrayList<String> noStockProducts;
    private HashSet<RegularUser> registeredRegularUsers;
    private HashSet<VipUser> registeredVipUsers;
    private HashMap<String, String> regularUsersNameAndPassword;
    private HashMap<String, String> vipUsersNameAndPassword;
    private OnlineStoreChatBot onlineStoreChatBot;
    private GraphicalPresenter presenter;


    public OnlineStore() {
        setDatabase(new ProductDatabase());
        this.database.loadProductDatabase();
        setNoStockProducts(new ArrayList<>());
        setRegisteredRegularUsers(new HashSet<>());
        setRegisteredVipUsers(new HashSet<>());
        this.registeredVipUsers.add(new VipUser("Pesho", "321", VipUserType.PLATINUM));
        setNoStockProducts(new ArrayList<>());
        setStoreOrderList(new ArrayList<>());
        setRegularUsersNameAndPassword(new HashMap<>());
        setVipUsersNameAndPassword(new HashMap<>());
        getVipUsersNameAndPassword().put("Pesho", "321");
        setOnlineStoreChatBot(new OnlineStoreChatBot());
        setGraphicalPresenter(new GraphicalPresenter());
    }


    //this method checks if the passed login credentials correspond to registered user name and password and if so we
    //return the user that is behind the credentials//otherwise we return a guest user
    public User checkCredentialsFromLogin(String username, String password) {
        for (String vipUserName : getVipUsersNameAndPassword().keySet()) {
            if (username.equals(vipUserName) && password.equals(getVipUsersNameAndPassword().get(vipUserName))) {
                for (VipUser user : getRegisteredVipUsers()) {
                    if (user.getUserName().equals(username)) {
                        System.out.println(MESSAGE_FOR_SUCCESSFUL_LOGIN + user.getUserName());
                        return user;
                    }
                }
            }
        }
        for (String regularUserName : getRegularUsersNameAndPassword().keySet()) {
            if (username.equals(regularUserName) && password.equals(getRegularUsersNameAndPassword().get(regularUserName))) {
                for (RegularUser user : getRegisteredRegularUsers()) {
                    if (user.getUserName().equals(username)) {
                        System.out.println(MESSAGE_FOR_SUCCESSFUL_LOGIN + user.getUserName());
                        return user;
                    }
                }
            }
        }
        System.out.println(MESSAGE_FOR_UNSUCCESSFUL_LOGIN);
        return new GuestUser();
    }

    public User assignGuestUser() {
        System.out.println(SIGNED_AS_GUEST_USER_MESSAGE);
        return new GuestUser();
    }

    public GraphicalPresenter getGraphicalPresenter() {
        return presenter;
    }

    //method that handles the return of a standard product if the product is not standard then we throw exception and
    //do not return it otherwise restock with the given amount and return money to user
    public void handleReturnOfProduct(int quantity, Product product) {
        try {
            if (!(product instanceof CustomProduct)) {
                ((StandardProduct) product).increaseStockAfterReturn(quantity, this);
                double priceToReturn = (product.getPrice() * quantity);
                System.out.println(MESSAGE_FOR_COMPLETED_RETURN_OF_PRODUCT);
                System.out.println("Returned amount of money: " + priceToReturn);
            } else {
                throw new StoreInteractionException(MESSAGE_FOR_IMPOSSIBLE_RETURN);
            }

        } catch (StoreInteractionException sie) {
            System.out.println(MESSAGE_FOR_IMPOSSIBLE_RETURN);
        }
    }

    public String showInformationForShipping() {
        StringBuilder shippingInfo = new StringBuilder();

        shippingInfo
                .append("1. Free shipping (Price: 0.00 lv.) ")
                .append("2. Standard shipping (Price: 5.00 lv.) ")
                .append("3. Express shipping (Price: 10.00 lv.)");

        return shippingInfo.toString();
    }

    public void processOrder(User user, Shipping shipping) throws IOException {
        System.out.println(MESSAGE_FOR_ORDER_PROCESSING);

        double totalCost = 0;

        HashMap<Product, Integer> productAndQuantityOrdered = user.getCart().getProductAndQuantityOrdered();

        for (Product orderedProduct : productAndQuantityOrdered.keySet()) {
            int orderedQuantity = productAndQuantityOrdered.get(orderedProduct);
            int stockOfProduct = orderedProduct.getStock();
            if (stockOfProduct >= orderedQuantity) {
                if (orderedProduct instanceof CustomProduct) {
                    totalCost = totalCost +
                            orderedProduct.getPrice() + ((CustomProduct) orderedProduct).getCostOfCustomisation();

                } else {
                    totalCost = totalCost +
                            orderedProduct.getPrice() * productAndQuantityOrdered.get(orderedProduct);
                }
                //setting the product's stock in store's database to correspond to the actual supply after the user's order
                this.database.products.stream()
                        .filter(product -> product.getId() == orderedProduct.getId())
                        .findFirst()
                        .ifPresent(product -> {
                            product.setStock(product.getStock() - orderedQuantity);
                            //adding the product to no stock product's list to keep track of products availability
                            if (product.getStock() == 0) {
                                this.noStockProducts.add(product.toString());
                            }
                        });


            } else {

                switch (onlineStoreChatBot.interactWithUserWhenStockIsLow(MESSAGE_FOR_NOT_ENOUGH_STOCK, orderedProduct.toString(), stockOfProduct)) {
                    case "yes":
                        totalCost = totalCost +
                                orderedProduct.getPrice() * stockOfProduct;

                        //setting the product's stock in store's database to correspond to the actual supply after the user's order
                        this.database.products.stream()
                                .filter(product -> product.getId() == orderedProduct.getId())
                                .findFirst()
                                .ifPresent(product -> {
                                    product.setStock(product.getStock() - stockOfProduct);
                                    //adding the product to no stock product's list to keep track of products availability
                                    if (product.getStock() == 0) {
                                        this.noStockProducts.add(product.toString());
                                    }
                                });
                        productAndQuantityOrdered.put(orderedProduct, stockOfProduct);
                        break;

                    default:
                        System.exit(0);
                }
            }
        }


        if (getRegisteredVipUsers().contains(user)) {
            switch (((VipUser) user).getType()) {

                case SILVER:
                    totalCost -= totalCost * DISCOUNT_FOR_SILVER_USER;
                    break;
                case GOLD:
                    totalCost -= totalCost * DISCOUNT_FOR_GOLD_USER;
                    break;
                case PLATINUM:
                    totalCost -= totalCost * DISCOUNT_FOR_PLATINUM_USER;
                    break;
            }

        }
        totalCost += shipping.getPrice();

        Order userOrder = new Order(user, shipping, totalCost);

        storeOrderList.add(userOrder);
        user.addOrderToUserCompletedOrders(userOrder);

        System.out.println(MESSAGE_FOR_ORDER_COMPLETED);


        //creating a graphical presenter to show the user his order and the message for encouragement
        try {
            //GraphicalPresenter presenter = new GraphicalPresenter();

            //simulate the order processing time
            Thread.sleep(2000);

            this.presenter
                    .presentUserOrderOnCompletion(userOrder.toString(), MESSAGE_FOR_ENCOURAGING_SHOPPING_AGAIN);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public OnlineStoreChatBot getOnlineStoreChatBot() {
        return onlineStoreChatBot;
    }

    public ProductDatabase getDatabase() {
        return database;
    }

    private ArrayList<Order> getStoreOrderList() {
        return storeOrderList;
    }

    private int getStoreOrdersCount() {
        return storeOrderList.size();
    }


    private ArrayList<String> getNoStockProducts() {
        return noStockProducts;
    }

    private HashSet<RegularUser> getRegisteredRegularUsers() {
        return registeredRegularUsers;
    }

    private HashSet<VipUser> getRegisteredVipUsers() {
        return registeredVipUsers;
    }

    private void setStoreOrderList(ArrayList<Order> storeOrderList) {
        this.storeOrderList = storeOrderList;
    }

    private void setDatabase(ProductDatabase database) {
        this.database = database;
    }


    private void setNoStockProducts(ArrayList<String> noStockProducts) {
        this.noStockProducts = noStockProducts;
    }


    private void setRegisteredRegularUsers(HashSet<RegularUser> registeredRegularUsers) {
        this.registeredRegularUsers = registeredRegularUsers;
    }

    private void setOnlineStoreChatBot(OnlineStoreChatBot onlineStoreChatBot) {
        this.onlineStoreChatBot = onlineStoreChatBot;
    }

    private void setRegisteredVipUsers(HashSet<VipUser> registeredVipUsers) {
        this.registeredVipUsers = registeredVipUsers;
    }

    private HashMap<String, String> getRegularUsersNameAndPassword() {
        return regularUsersNameAndPassword;
    }

    private HashMap<String, String> getVipUsersNameAndPassword() {
        return vipUsersNameAndPassword;
    }

    private void setRegularUsersNameAndPassword(HashMap<String, String> regularUsersNameAndPassword) {
        this.regularUsersNameAndPassword = regularUsersNameAndPassword;
    }

    private void setVipUsersNameAndPassword(HashMap<String, String> vipUsersNameAndPassword) {
        this.vipUsersNameAndPassword = vipUsersNameAndPassword;
    }

    private void setGraphicalPresenter(GraphicalPresenter presenter) {
        this.presenter = presenter;
    }

    private void addProductToListOFNoStockItems(Product product) {
        this.noStockProducts.add(product.toString());
    }
}



