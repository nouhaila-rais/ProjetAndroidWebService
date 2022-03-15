package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.repositories.IProductRepository;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        for (Product p:list) {
            p.setComments(commentServices.getCommentByProduct(p.getId()));
            p.setBorrows(borrowServices.getBorrowByProduct(p.getId()));
            p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
        }
        return list;
    }

    public Product getProductById(long id){

        Product p = productRepostory.findById(id);
        p.setComments(commentServices.getCommentByProduct(p.getId()));
        p.setBorrows(borrowServices.getBorrowByProduct(p.getId()));
        p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
        return p;
    }

    public List<Product> getProductByCategory(String category){ return productRepostory.findProductsByCategory(category);}

    public  List<Product> getProductByName(String name){ return productRepostory.findProductsByName(name);}

    public void delete(Product product){
        productRepostory.delete(product);
    }

    public void deleteById(Long id){
        productRepostory.deleteById(id);
    }

}
