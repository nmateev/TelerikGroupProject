package onlineshop.store;

import onlineshop.shipping.ExpressShipping;
import onlineshop.shipping.FreeShipping;
import onlineshop.shipping.Shipping;
import onlineshop.shipping.StandardShipping;
import onlineshop.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OnlineStoreChatBot {
    private static final String PROMPT_USER_FOR_SELECTION = "\n\nPlease select the items you want. Only type the number of the product and wanted quantity in separate lines";
    private static final String WHEN_READY_OR_WANT_TO_SHOP_MORE_MESSAGE = "Type in \"c\" to continue or \"ready\" to order the items in your cart!";
    private static final String INVALID_SELECTION = "Invalid selection.Try again!";
    private static final String TYPE_IN_NAME_FOR_DELIVERY_MESSAGE = "Type in name for delivery:";
    private static final String TYPE_IN_NUMBER_FOR_DELIVERY_MESSAGE = "Type in number for delivery:";
    private static final String TYPE_IN_ADDRESS_FOR_DELIVERY_MESSAGE = "Type in address for delivery:";
    private static final String DELIVERY_OPTIONS_MESSAGE = "Select the type of shipping, input number: ";
    private static final String SELECT_PRODUCT_MESSAGE = "Select product number: ";
    private static final String SELECT_QUANTITY_MESSAGE = "Select quantity: ";
    private static final String NOT_ENOUGH_STOCK_MESSAGE = "Not enough stock! Stock supply is: ";


    public String interactWithUserWhenStockIsLow(String message, String productInfo, int parameter) throws IOException {
        System.out.printf(message, parameter, productInfo);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String userInput = in.readLine();
        return userInput;
    }

    public void interactWithUser(OnlineStore onlineStore, User user, ArrayList<Product> products, BufferedReader in) throws IOException {
        System.out.println(PROMPT_USER_FOR_SELECTION);
        int productNumber;
        int quantity;
        System.out.println(SELECT_PRODUCT_MESSAGE);
        String productNumberInput = in.readLine();
        while (!validateInputForProduct(products.size(), productNumberInput)) {
            System.out.println(INVALID_SELECTION);
            System.out.println(SELECT_PRODUCT_MESSAGE);
            productNumberInput = in.readLine();
        }
        productNumber = Integer.parseInt(productNumberInput) - 1;
        System.out.println(SELECT_QUANTITY_MESSAGE);
        String quantityInput = in.readLine();
        while (!validateInputForQuantity(products, productNumber, quantityInput)) {
            System.out.println(INVALID_SELECTION);
            System.out.println(SELECT_QUANTITY_MESSAGE);
            quantityInput = in.readLine();
        }
        quantity = Integer.parseInt(quantityInput);

        user.addProductToCart(products.get(productNumber), quantity);
        System.out.println(WHEN_READY_OR_WANT_TO_SHOP_MORE_MESSAGE);
        String userCommand = in.readLine();
        while (!(userCommand.equalsIgnoreCase("c") || userCommand.equalsIgnoreCase("ready"))) {
            System.out.println(INVALID_SELECTION);
            userCommand = in.readLine();
        }
        while (!userCommand.equalsIgnoreCase("ready")) {

            while (!(userCommand.equalsIgnoreCase("c") || userCommand.equalsIgnoreCase("ready"))) {
                System.out.println(INVALID_SELECTION);
                userCommand = in.readLine();
            }
            while (userCommand.equalsIgnoreCase("c")) {

                System.out.println(SELECT_PRODUCT_MESSAGE);
                productNumberInput = in.readLine();
                while (!validateInputForProduct(products.size(), productNumberInput)) {
                    System.out.println(INVALID_SELECTION);
                    System.out.println(SELECT_PRODUCT_MESSAGE);
                    productNumberInput = in.readLine();
                }
                productNumber = Integer.parseInt(productNumberInput) - 1;
                System.out.println(SELECT_QUANTITY_MESSAGE);
                quantityInput = in.readLine();
                while (!validateInputForQuantity(products, productNumber, quantityInput)) {
                    System.out.println(INVALID_SELECTION);
                    System.out.println(SELECT_QUANTITY_MESSAGE);
                    quantityInput = in.readLine();
                }
                quantity = Integer.parseInt(quantityInput);
                user.addProductToCart(products.get(productNumber), quantity);
                System.out.println(WHEN_READY_OR_WANT_TO_SHOP_MORE_MESSAGE);

                userCommand = in.readLine();
                while (!(userCommand.equalsIgnoreCase("c") || userCommand.equalsIgnoreCase("ready"))) {
                    System.out.println(INVALID_SELECTION);
                    userCommand = in.readLine();
                }
            }
        }
        if (userCommand.equalsIgnoreCase("ready")) {
            System.out.println(TYPE_IN_NAME_FOR_DELIVERY_MESSAGE);
            String name = in.readLine();
            System.out.println(TYPE_IN_NUMBER_FOR_DELIVERY_MESSAGE);
            String number = in.readLine();
            System.out.println(TYPE_IN_ADDRESS_FOR_DELIVERY_MESSAGE);
            String delivery = in.readLine();

            System.out.println(DELIVERY_OPTIONS_MESSAGE + onlineStore.showInformationForShipping());
            int optionForDelivery = 0;
            boolean correctInput = false;
            while (!correctInput) {
                String stringOption = in.readLine();
                if (stringOption.length() == 0) {
                    System.out.println(INVALID_SELECTION);
                } else if (Character.isDigit(stringOption.charAt(0)) && stringOption.length() < 2) {
                    optionForDelivery = Integer.parseInt(stringOption);
                    if (optionForDelivery > 0 && optionForDelivery < 4) {
                        correctInput = true;
                    } else {
                        System.out.println(INVALID_SELECTION);
                    }
                } else {
                    System.out.println(INVALID_SELECTION);
                }
            }
            Shipping shipping = null;
            switch (optionForDelivery) {
                case 1:
                    shipping = new FreeShipping(name, number, delivery);
                    break;
                case 2:
                    shipping = new StandardShipping(name, number, delivery);
                    break;
                case 3:
                    shipping = new ExpressShipping(name, number, delivery);
                    break;
            }
            user.buyProductsFromCart(shipping, onlineStore);
        }
    }


    private boolean validateInputForProduct(int productsResultSize, String input) {
        if (input.length() == 0) {
            return false;
        }
        //iterate trough the user's input and check if there are only digits
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        //if all chars are digits we continue to check it the number is in the right bounds
        int numberInput = Integer.parseInt(input);
        if (numberInput > 0 && numberInput <= productsResultSize) {
            return true;
        }
        //if the input is not correct return false
        return false;
    }

    private boolean validateInputForQuantity(ArrayList<Product> products, int selectedProduct, String input) {
        if (input.length() == 0) {
            return false;
        }
        //iterate trough the user's input and check if there are only digits
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        //if all chars are digits we continue to check it the quantity is not negative or zero and the store has enough stock
        int quantityWanted = Integer.parseInt(input);
        int productStock = products.get(selectedProduct).getStock();
        if (quantityWanted > 0 && quantityWanted <= productStock) {
            return true;
        }
        //if the stock is not sufficient , return false
        System.out.println(NOT_ENOUGH_STOCK_MESSAGE + productStock);
        return false;
    }

    public void throwInteractionException(String message) throws StoreInteractionException {
        throw new StoreInteractionException(message);
    }
}
