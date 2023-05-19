package presentation;

import dao.AbstractDAO;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * Class used to create a window in which There are generated some fields for the columns of a table in which the employee can enter the data of a new row and commit
 */
public class insertWindow extends JFrame {
    private JPanel jPanel;
    private GridBagConstraints gbc;
    private String className;
    private JLabel []name = new JLabel[5];
    Class<?> bll = null;
    private JTextField []pane = new JTextField[5];
    /**
     * constructor that calls the method generateViews
     */
    public insertWindow(Class<?> clazz){
        className = clazz.getSimpleName();
        setTitle("Insert");
        pack();
        setVisible(true);
        jPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);
        generateViews(clazz);
        add(jPanel);
    }
    /**
     * method that ads the button listener and genereates through reflection the table column names and the textfields in which the user enters data
     */
    private void generateViews(Class<?> object1){

        JButton jButton = new JButton();
        String bll1 = "bll."+className+"BLL";
        int ind=0;
        try {
            bll = Class.forName(bll1);
            // Use the class object 'clazz' for further operations
        } catch (ClassNotFoundException e) {
            // Handle class not found exception
        }
        for (Field field : object1.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getName().equals("id")){
                continue;
            }
            try {
                gbc.gridx=0;
                name[ind] = new JLabel(field.getName());
                jPanel.add(name[ind],gbc);
                gbc.gridx++;
                pane[ind] = new JTextField();
                pane[ind].setPreferredSize(new Dimension(200,40));
                jPanel.add(pane[ind],gbc);
                gbc.gridy++;
                ind++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        jButton.setText("Add");
        jButton.addActionListener(e -> {
            try {
                Object obj = createInstance(object1);
                int cur=0;
                for (Field field : obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")){
                        AbstractDAO<?> dao = new AbstractDAO<>(object1);
                        field.set(obj, dao.findMaxId()+1);
                        continue;
                    }
                        String text = pane[cur++].getText();
                         if(text.matches("-?\\d+(\\.\\d+)?"))
                            field.set(obj,Integer.parseInt(text));
                         else field.set(obj,text);
                }
                Object obj1 = createInstance(bll);
                Method insertMethod = obj1.getClass().getMethod("insert", obj.getClass());
                insertMethod.invoke(obj1, obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        jPanel.add(jButton,gbc);
    }/**
     * methods takes a class and returns a new instance of that class
     */
    public static <T> T createInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
