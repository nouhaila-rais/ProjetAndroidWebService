package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.repositories.IProductRepository;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class ProductServices {
    @Autowired
    IProductRepository productRepostory;

    @Autowired
    IUserRepository userRepostory;

    @Autowired
    CommentServices commentServices;

    @Autowired
    BorrowServices borrowServices;

    @Autowired
    RequestBorrowServices requestBorrowServices;

    //SecurityUtils securityUtils = new SecurityUtils();

    public void save(Product product){
        productRepostory.save(product);
    }

    public Iterable<Product> listProduct(){

        Iterable<Product> list = productRepostory.findAll();

        for (Product p : list) {
            p.setComments(commentServices.getCommentByProduct(p.getId()));
            p.setBorrows(borrowServices.getBorrowByProduct(p.getId()));
            p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
        }

        return list;
    }

    public Product getProductById(long id){

        Product p = productRepostory.findById(id);
        if(p!=null){
            p.setComments(commentServices.getCommentByProduct(p.getId()));
            p.setBorrows(borrowServices.getBorrowByProduct(p.getId()));
            p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
        }
        return p;
    }

    public List<Product> getProductByCategory(String category){
        List<Product> list = productRepostory.findProductsByCategory(category);
        if(list!=null) {
            for (Product p : list) {
                p.setComments(commentServices.getCommentByProduct(p.getId()));
                p.setBorrows( borrowServices.getBorrowByProduct(p.getId()));
                p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
            }
        }
        return list;
    }

    public  List<Product> getProductByName(String name){
        List<Product> list = productRepostory.findProductsByName(name);
        if(list!=null){
            for (Product p:list) {
                p.setComments(commentServices.getCommentByProduct(p.getId()));
                p.setBorrows((borrowServices.getBorrowByProduct(p.getId())));
                p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
            }
        }

        return list;
    }


/*
    public List<Product> getProductOfCurrentUser(String username){
        return productRepostory.findAllForCurrentUser(username);
    }
*/


    public void delete(Product product){
        productRepostory.delete(product);
    }

    public void deleteById(Long id){
        productRepostory.deleteById(id);
    }


    public List<Product> getProductsByKeyWord(String key){
        Iterable<Product> list= listProduct();
        Vector<Product> res =new Vector<Product>();
        for (Product product : list) {
            if (product.getName().toLowerCase().contains(key) || product.getCategory().toLowerCase().contains(key) || product.getType().toLowerCase().contains(key))
                res.add(product);
        }
        return res;
    }

    public List<Product> getProductByUser(long user){
        List<Product> list = productRepostory.findProductsByUser(user);
        if(list!=null){
            for (Product p : list) {
                p.setComments(commentServices.getCommentByProduct(p.getId()));
                p.setBorrows((borrowServices.getBorrowByProduct(p.getId())));
                p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
            }
        }
        return list;
    }

}
