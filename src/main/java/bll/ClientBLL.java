package bll;

import dao.AbstractDAO;
import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientDAO;
    public static int idLimit;
    public ClientBLL(){
        clientDAO = new ClientDAO();
        idLimit = clientDAO.findMaxId();
    }
    public Client findClientById(int id){
        Client client = clientDAO.findById(id);
        if(client==null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }
    public void insert(Client client){
        clientDAO.insert(client);
    }
    public void update(Client client){
        clientDAO.update(client);
    }
    public List<Client> findAll(){
        return clientDAO.findAll();
    }
    public void remove(int id){
        clientDAO.remove(id);
    }
}
