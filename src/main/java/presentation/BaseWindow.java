package presentation;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.ParameterizedType;
/**
* Class used to create a window in which There are buttons that open insertWindow of the specific type or viewWindow
 */
public class BaseWindow extends JFrame{
    JPanel jPanel;
    private final Class<?> clazz;
    @SuppressWarnings("unchecked")
    /**
     * constructor sets the frame specifics and ads the two buttons
     */
    public BaseWindow(Class<?> clazz){
        this.clazz = clazz;
        String name = clazz.getSimpleName();
        setTitle(name+"s");
        pack();
        setVisible(true);
        setBounds(0,300,520,500);
        jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);

        JButton newButton = new JButton("New");
        jPanel.add(newButton,gbc);
        newButton.addActionListener(e -> {
            new insertWindow(clazz).setBounds(this.getBounds());
        });
        gbc.gridx++;
        JButton viewButton = new JButton("View");
        jPanel.add(viewButton,gbc);
        viewButton.addActionListener(e -> {
            new viewWindow(clazz).setBounds(this.getBounds());
        });
        add(jPanel);
    }
}
