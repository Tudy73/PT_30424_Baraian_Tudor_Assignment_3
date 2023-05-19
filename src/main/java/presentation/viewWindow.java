package presentation;

import dao.AbstractDAO;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Class used to create a window in which the user sees the current rows from the table and can make changes and/or remove some fields
 */
public class viewWindow extends JFrame{
    private JPanel jPanel;

    private JLabel []jLabel = new JLabel[15];
    private JLabel[] header = new JLabel[15];
    private GridBagConstraints gbc;
    private String className;
    Class<?> bll = null;
    private JTextField [][]pane = new JTextField[20][15];
    private JCheckBox[] checker = new JCheckBox[15];
    /**
     * constructor calls the generateViews method
     */
    public viewWindow(Class<?> clazz){
        className = clazz.getSimpleName();
        setTitle("Table");
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
     * method generates the table column names, the textfields with the data, the checkboxes for each of them, and the modify button
     */
    private void generateViews(Class<?> object1){

        JButton jButton = new JButton();
        String bll1 = "bll."+className+"BLL";
        System.out.println(bll1);
        try {
            bll = Class.forName(bll1);
            // Use the class object 'clazz' for further operations
        } catch (ClassNotFoundException e) {
            // Handle class not found exception
        }
        int i=0;
        for (Field field : object1.getDeclaredFields()) {
            field.setAccessible(true);
            header[i]=new JLabel(field.getName());
            jPanel.add(header[i++],gbc);
            gbc.gridx++;
        }
        AbstractDAO<?> dao = new AbstractDAO<>(object1);
        List<?> list =  dao.findAll();
        int cur1 =0;
        for( Object list1:list){
            i=0;
            int ok=0;
            gbc.gridy++;
            gbc.gridx=0;
            for (Field field : object1.getDeclaredFields()) {
                field.setAccessible(true);
                if(ok==0){
                    ok=1;
                    try {
                        jLabel[cur1]=new JLabel(field.get(list1).toString());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    jPanel.add(jLabel[cur1],gbc);
                    gbc.gridx++;
                    continue;
                }
                try {
                    if (field.get(list1).toString().matches(("-?\\d+\\.\\d+"))){
                        double number = (double)field.get(list1);
                        DecimalFormat decimalFormat = new DecimalFormat("#.####");
                        String formattedNumber = decimalFormat.format(number);
                        pane[cur1][i]=new JTextField(formattedNumber);
                    }
                    else
                        pane[cur1][i]=new JTextField(field.get(list1).toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                jPanel.add(pane[cur1][i++],gbc);
                gbc.gridx++;
            }
            checker[cur1]=new JCheckBox();
            jPanel.add(checker[cur1],gbc);
            cur1++;
        }
        jButton.setText("Modify");
        jButton.addActionListener(e -> {
            boolean check = false;
            try {
                Object obj = createInstance(object1);
                System.out.println(obj.getClass().getDeclaredFields().length);
                int j=0;
                Object obj1 = createInstance(bll);
                for( Object list1:list) {
                    int cur = 0;
                    int ok = 0;
                    int id=0;
                    for (Field field : obj.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        if (ok == 0) {
                            field.set(obj, Integer.parseInt(jLabel[j].getText()));
                            id = Integer.parseInt(jLabel[j].getText());
                            ok = 1;
                            continue;
                        }
                        String text = pane[j][cur++].getText();
                        if (text.matches("\\d+")) {
                            field.set(obj, Integer.parseInt(text));
                        } else if (text.matches(("-?\\d+(\\.\\d+)?([eE]-?\\d+)?")))
                            field.set(obj, Double.parseDouble(text));
                        else field.set(obj, text);
                    }
                    if(checker[j].isSelected()){
                        check=true;
                        obj1.getClass().getMethod("remove",int.class).invoke(obj1,id);
                    }
                    j++;
                    Method insertMethod = obj1.getClass().getMethod("update", obj.getClass());
                    insertMethod.invoke(obj1, obj);
                }
                if(check){
                    new viewWindow(object1).setBounds(this.getBounds());
                    this.dispose();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        gbc.gridy++;
        jPanel.add(jButton,gbc);
    }
    /**
     * creates an instance of the class provided as parameter
     */
    public static <T> T createInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}

