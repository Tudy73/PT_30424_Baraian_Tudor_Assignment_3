package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CommandWindow extends JFrame {
    private JPanel jPanel;

    private JComboBox<String> clients,products;
    private JTextField quantityText;
    private JButton button;
    private GridBagConstraints gbc;
    public CommandWindow(){
        setTitle("Order");
        pack();
        setVisible(true);
        jPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);
        List<Client> clientList = (new ClientBLL()).findAll();
        clients = new JComboBox<>();
        for(Client client: clientList){
            clients.addItem(client.getName());
        }
        List<Product> productList = (new ProductBLL()).findAll();
        products = new JComboBox<>();
        for(Product product: productList){
            products.addItem(product.getName());
        }
        quantityText = new JTextField();
        quantityText.setPreferredSize(new Dimension(80,40));
        button = new JButton("Purchase");
        jPanel.add(clients,gbc);
        gbc.gridy++;
        jPanel.add(products,gbc);
        gbc.gridy++;
        jPanel.add(quantityText,gbc);
        gbc.gridy++;
        jPanel.add(button,gbc);
        add(jPanel);
        button.addActionListener(e -> {
            Product product = productList.get(products.getSelectedIndex());
            int quantity = Integer.parseInt(quantityText.getText());
            Client client = clientList.get(clients.getSelectedIndex());
            if(product.getQuantity()< quantity|| client.getBalance()<product.getPrice()*quantity){
                System.out.println("Error");
                return;
            }
            OrderBLL orderBLL = new OrderBLL();
            Order order = new Order(orderBLL.getMaxId()+1,clientList.get(clients.getSelectedIndex()).getId(),productList.get(products.getSelectedIndex()).getId());
            orderBLL.insert(order);
            ClientBLL clientBLL = new ClientBLL();
            client.setBalance(client.getBalance()-product.getPrice()*quantity);
            product.setQuantity(product.getQuantity()-quantity);
            (new ProductBLL()).update(product);
            clientBLL.update(client);
        });
    }
}
