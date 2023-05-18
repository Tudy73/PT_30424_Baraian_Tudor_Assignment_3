package presentation;

import javax.swing.*;
import java.awt.*;

public class OrderWindow extends JFrame {
    JPanel jPanel;
    public OrderWindow(){
        setTitle("Orders");
        pack();
        setVisible(true);
        jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);
        JButton newButton = new JButton("New");
        jPanel.add(newButton,gbc);
        newButton.addActionListener(e -> {
            new CommandWindow().setBounds(this.getBounds());
        });


        add(jPanel);
    }
}
