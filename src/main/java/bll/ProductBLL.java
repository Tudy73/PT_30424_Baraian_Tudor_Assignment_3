package bll;

import dao.ProductDAO;
import model.Client;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductDAO productDAO;
    public ProductBLL(){
        productDAO = new ProductDAO();
    }
    public Product findProductById(int id){
        Product product = productDAO.findById(id);
        if(product==null){
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }
    public void insert(Product product){
        productDAO.insert(product);
    }
    public void update(Product product){
        productDAO.update(product);
    }
    public List<Product> findAll(){
        return productDAO.findAll();
    }
    public void remove(int id){
        productDAO.remove(id);
    }
}
