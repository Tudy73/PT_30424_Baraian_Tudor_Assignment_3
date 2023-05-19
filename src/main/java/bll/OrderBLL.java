package bll;

import dao.OrderDAO;
import model.Client;
import model.Order;

import java.util.NoSuchElementException;

/**
 * Class used to create the order methods that access the database
 */
public class OrderBLL {
    private OrderDAO orderDAO;
    public OrderBLL(){
        orderDAO = new OrderDAO();
    }
    public Order findOrderById(int id){
        Order order = orderDAO.findById(id);
        if(order==null){
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }
    public void insert(Order order){
        orderDAO.insert(order,1);
    }
    public void update(Order order){
        orderDAO.update(order);
    }
    public int getMaxId(){
        return orderDAO.findMaxId();
    }
}
