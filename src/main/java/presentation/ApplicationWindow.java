package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
/**
 * Class used to create a window in which There are buttons for client, product, and order windows
 */
public class ApplicationWindow extends JFrame{
    JPanel jPanel;
    /**
     * constructor sets the specifics for the Jframe and ads the buttons presented
     */
    public ApplicationWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("init");
        pack();
        setVisible(true);
        setBounds(513,0,514,300);
        jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);

        JButton clientButton = new JButton("Client");
        jPanel.add(clientButton,gbc);
        clientButton.addActionListener(e -> {
            new BaseWindow(Client.class).setBounds(0,300,520,500);
        });

        gbc.gridx++;
        JButton orderButton = new JButton("Order");
        jPanel.add(orderButton,gbc);
        orderButton.addActionListener(e -> {
            new OrderWindow().setBounds(513,300,520,500);
        });

        gbc.gridx++;
        JButton productButton = new JButton("Product");
        jPanel.add(productButton,gbc);
        productButton.addActionListener(e -> {
            new BaseWindow(Product.class).setBounds(1026,300,520,500);
        });
        add(jPanel);
    }
}
