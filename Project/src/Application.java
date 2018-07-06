import onlineshop.store.OnlineStore;
import onlineshop.store.Product;
import onlineshop.store.StoreInteractionException;
import onlineshop.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


public class Application {
    private static final String WELCOME_MESSAGE = "Welcome to the online store! Happy shopping.\n";
    private static final String CHECK_IF_REGISTERED = "Do you have an account? If so type in yes!";
    private static final String PROMPT_USER_FOR_HIS_CREDENTIALS = "Log in your username and password in the fields below";
    private static final String USERNAME_FIELD = "Username: ";
    private static final String PASSWORD_FIELD = "Password: ";
    private static final String SHOW_MAIN_STORE_OPTIONS = "What do you want to see?\n" +
            "1.List all products\n2.List products by price in ascending order\n" +
            "3.List products by price in descending order\n4.Search store by products name, brand, category,below given " +
            "price,above given price or description\n5.Exit store";
    private static final String INVALID_SELECTION = "Invalid selection.Try again!";
    private static final String PROMPT_USER_FOR_SELECTION_OF_SEARCH_OPTIONS = "Select By what do you want to search the store's products - input number:\n1. By name\n2.By brand\n" +
            "3.By category\n4. By price - below given price\n5. By price - above given price\n";
    private static final String NO_RESULT_MESSAGE = "Your search query returned zero results.Try again!";
    private static final int MIN_MENU_OPTION_BOUND = 0;
    private static final int MAX_MENU_OPTION_BOUND = 6;

    public static void main(String[] args) throws IOException {

        OnlineStore onlineStore = new OnlineStore();

        onlineStore.getGraphicalPresenter().presentMessage(WELCOME_MESSAGE, CHECK_IF_REGISTERED);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        User user;

        switch (reader.readLine()) {

            //if the user says he is registered we check his credentials
            case "yes":
                onlineStore.getGraphicalPresenter().presentMessage(PROMPT_USER_FOR_HIS_CREDENTIALS, USERNAME_FIELD);

                String userName = reader.readLine();

                onlineStore.getGraphicalPresenter().presentMessage(PASSWORD_FIELD);
                String userPassword = reader.readLine();

                user = onlineStore.checkCredentialsFromLogin(userName, userPassword);
                break;
            default:

                //when the user is not registered we sign him as guest user
                user = onlineStore.assignGuestUser();
        }
        onlineStore.getGraphicalPresenter().presentMessage(SHOW_MAIN_STORE_OPTIONS);
        String command = reader.readLine();

        //checks it the selected command is corresponding to the presented store menu options from 1 to 5
        while (true) {
            if (((command.equals("1"))
                    || (command.equals("2"))
                    || (command.equals("3"))
                    || (command.equals("4")) ||
                    (command.equals("5")))) {
                break;
            } else {
                onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                command = reader.readLine();
            }

        }
        //switch cases in correspondence of the user's selected menu option
        switch (command) {
            case "1":
                ArrayList<Product> allProducts = onlineStore
                        .getDatabase()
                        .getProducts();
                onlineStore.getGraphicalPresenter().presentDatabaseResult(allProducts);
                onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, allProducts, reader);
                break;
            case "2":
                ArrayList<Product> sortedAscendingOrder = onlineStore
                        .getDatabase()
                        .sortByAscendingPrice();
                onlineStore.getGraphicalPresenter().presentDatabaseResult(sortedAscendingOrder);
                onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, sortedAscendingOrder, reader);
                break;
            case "3":
                ArrayList<Product> sortedDescendingOrder = onlineStore
                        .getDatabase()
                        .sortByDescendingPrice();
                onlineStore.getGraphicalPresenter().presentDatabaseResult(sortedDescendingOrder);
                onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, sortedDescendingOrder, reader);
                break;
            case "4":
                onlineStore.getGraphicalPresenter().presentMessage(PROMPT_USER_FOR_SELECTION_OF_SEARCH_OPTIONS);
                boolean correct = false;
                int option = 0;
                while (!correct) {
                    String stringOption = reader.readLine();
                    if (stringOption.length() == 0) {
                        onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                        continue;
                    }
                    //checks if the input's length is less then 2 digits and ig it is in the range of 1 to 5 because the search options count is 5
                    if (Character.isDigit(stringOption.charAt(0)) && stringOption.length() < 2) {
                        option = Integer.parseInt(stringOption);
                        if (option > MIN_MENU_OPTION_BOUND && option < MAX_MENU_OPTION_BOUND) {
                            correct = true;
                        } else {
                            onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                        }
                    } else {
                        onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                    }
                }

                switch (option) {
                    case 1:
                        ArrayList<Product> matchingWantedName = null;
                        boolean isMatchingNameFound = false;
                        while (!isMatchingNameFound) {
                            onlineStore.getGraphicalPresenter().presentMessage("Select name:");
                            String nameWanted = reader.readLine();
                            try {
                                matchingWantedName = onlineStore
                                        .getDatabase()
                                        .searchByName(nameWanted);
                                if (matchingWantedName.size() < 1) {
                                    onlineStore.getOnlineStoreChatBot().throwInteractionException(NO_RESULT_MESSAGE);
                                } else {
                                    isMatchingNameFound = true;
                                }

                            } catch (StoreInteractionException sie) {
                                onlineStore.getGraphicalPresenter().presentMessage(NO_RESULT_MESSAGE);
                            }


                        }
                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingWantedName);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingWantedName, reader);

                        break;
                    case 2:
                        ArrayList<Product> matchingWantedBrand = null;
                        boolean isMatchingBrandFound = false;
                        while (!isMatchingBrandFound) {
                            onlineStore.getGraphicalPresenter().presentMessage("Select brand:");
                            String brandWanted = reader.readLine();
                            try {
                                matchingWantedBrand = onlineStore
                                        .getDatabase()
                                        .searchByBrand(brandWanted);
                                if (matchingWantedBrand.size() < 1) {
                                    onlineStore.getOnlineStoreChatBot().throwInteractionException(NO_RESULT_MESSAGE);
                                } else {
                                    isMatchingBrandFound = true;
                                }

                            } catch (StoreInteractionException sie) {
                                onlineStore.getGraphicalPresenter().presentMessage(NO_RESULT_MESSAGE);
                            }


                        }

                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingWantedBrand);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingWantedBrand, reader);
                        break;
                    case 3:
                        onlineStore.getGraphicalPresenter().presentMessage("Supported categories");
                        onlineStore.getDatabase().showDatabaseSupportedCategories();

                        ArrayList<Product> matchingWantedCategory = null;
                        boolean isInputValid = false;
                        while (!isInputValid) {
                            try {
                                onlineStore.getGraphicalPresenter().presentMessage("Select category:");
                                String category = reader.readLine();
                                matchingWantedCategory = onlineStore
                                        .getDatabase()
                                        .searchByCategory(category);
                            } catch (IllegalArgumentException iae) {
                                onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                            }
                           /* handling the case when the illegal argument exception is thrown and the result(matchingWantedCategory) of the query equals null, then we know the input was correct
                            and ask the user again for his input*/
                            if (!Objects.equals(matchingWantedCategory, null)) {
                                isInputValid = true;
                            }
                        }


                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingWantedCategory);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingWantedCategory, reader);
                        break;
                    case 4:
                        ArrayList<Product> matchingBelowPrice = null;
                        boolean isMatchingBelowPriceFound = false;
                        while (!isMatchingBelowPriceFound) {
                            onlineStore.getGraphicalPresenter().presentMessage("Select price:");
                            String belowGivenPrice = reader.readLine();
                            try {
                                matchingBelowPrice = onlineStore
                                        .getDatabase()
                                        .searchBelowGivenPrice(belowGivenPrice);
                                if (matchingBelowPrice.size() < 1) {
                                    onlineStore.getOnlineStoreChatBot().throwInteractionException(NO_RESULT_MESSAGE);
                                } else {
                                    isMatchingBelowPriceFound = true;
                                }

                            } catch (StoreInteractionException sie) {
                                onlineStore.getGraphicalPresenter().presentMessage(NO_RESULT_MESSAGE);
                            } catch (NumberFormatException nfe) {
                                onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                                onlineStore.getGraphicalPresenter().presentMessage("Enter numeric value!");
                            }
                        }

                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingBelowPrice);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingBelowPrice, reader);
                        break;
                    case 5:
                        ArrayList<Product> matchingAbovePrice = null;
                        boolean isMatchingAbovePriceFound = false;
                        while (!isMatchingAbovePriceFound) {
                            onlineStore.getGraphicalPresenter().presentMessage("Select price:");
                            String aboveGivenPrice = reader.readLine();
                            try {
                                matchingAbovePrice = onlineStore
                                        .getDatabase()
                                        .searchAboveGivenPrice(aboveGivenPrice);
                                if (matchingAbovePrice.size() < 1) {
                                    onlineStore.getOnlineStoreChatBot().throwInteractionException(NO_RESULT_MESSAGE);
                                } else {
                                    isMatchingAbovePriceFound = true;
                                }

                            } catch (StoreInteractionException sie) {
                                onlineStore.getGraphicalPresenter().presentMessage(NO_RESULT_MESSAGE);
                            } catch (NumberFormatException nfe) {
                                onlineStore.getGraphicalPresenter().presentMessage(INVALID_SELECTION);
                                onlineStore.getGraphicalPresenter().presentMessage("Enter numeric value!");
                            }
                        }

                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingAbovePrice);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingAbovePrice, reader);
                        break;
                    case 6:

                        ArrayList<Product> matchingGivenDescription = null;
                        boolean isMatchingDescriptionFound = false;
                        while (!isMatchingDescriptionFound) {
                            onlineStore.getGraphicalPresenter().presentMessage("Select description:");
                            String description = reader.readLine();
                            try {
                                matchingGivenDescription = onlineStore
                                        .getDatabase()
                                        .searchByDescription(description);
                                if (matchingGivenDescription.size() < 1) {
                                    onlineStore.getOnlineStoreChatBot().throwInteractionException(NO_RESULT_MESSAGE);
                                } else {
                                    isMatchingDescriptionFound = true;
                                }

                            } catch (StoreInteractionException sie) {
                                onlineStore.getGraphicalPresenter().presentMessage(NO_RESULT_MESSAGE);
                            }


                        }

                        onlineStore.getGraphicalPresenter().presentDatabaseResult(matchingGivenDescription);
                        onlineStore.getOnlineStoreChatBot().interactWithUser(onlineStore, user, matchingGivenDescription, reader);
                        break;
                }
                break;
            case "5":
                onlineStore.getGraphicalPresenter().presentMessage("See you back again!");
                System.exit(0);
                break;
        }
    }
}
