package dao;

import connection.ConnectionFactory;
import model.Order;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
/**
 * Class used to create the order data access methods and that has the abstractDAO methods
 */
public class OrderDAO extends AbstractDAO<Order>{
    public OrderDAO() {
        super();
    }
    /**
     * method for inserting the order in the database
     */
    public void insert(Order t,int i) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("`Order`");
        sb.append(" (id, idClient, idProduct) VALUES (");
        for (Field field : t.getClass().getDeclaredFields()) {
            sb.append('?');
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append(");");
        System.out.println(sb);
        String query = sb.toString();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int contor=0;
            for (Field field : t.getClass().getDeclaredFields()) {
                contor++;
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(t);
                    statement.setObject(contor,value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Order.class.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
