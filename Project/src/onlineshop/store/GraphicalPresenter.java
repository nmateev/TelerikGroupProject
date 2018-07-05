package onlineshop.store;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicalPresenter {

    public void presentUserOrderOnCompletion(String orderInfo, String comeBackAgainMessage) {
        StringBuilder finalMessage = new StringBuilder();
        finalMessage
                .append(orderInfo)
                .append("\n\n\n")
                .append(comeBackAgainMessage);
        new OrderCompletedFrame(finalMessage.toString());
    }

    public void presentDatabaseResult(ArrayList<Product> products) {
        final int[] index = {1};
        products.forEach((Product product) -> {
            System.out.print(index[0]);
            System.out.print(".");
            product.showProduct();
            index[0]++;
        });
    }

    public void presentMessage(String message) {
        System.out.println(message);
    }

    public void presentMessage(String message, String secondMessage) {
        System.out.println(message);
        System.out.println(secondMessage);
    }

    //creating custom frame class to represent the information for the user order and come back again message
    static class OrderCompletedFrame extends JFrame {
        OrderCompletedFrame(String orderInfo) {
            super("Order completed!");
            getContentPane().setBackground(Color.LIGHT_GRAY);
            JTextArea text = new JTextArea(orderInfo);
            text.setWrapStyleWord(true);
            text.setEditable(false);
            text.setOpaque(false);
            Font font = new Font("Arial", Font.BOLD, 14);
            text.setFont(font);
            add(text);
            setVisible(true);
            setResizable(true);
            setSize(1100, 500);
            setLocation(20, 20);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}
