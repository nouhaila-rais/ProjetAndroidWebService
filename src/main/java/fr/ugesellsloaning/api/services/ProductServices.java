package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.WaitingList;
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
    CommentServices commentServices;

    @Autowired
    BorrowServices borrowServices;

    @Autowired
    RequestBorrowServices requestBorrowServices;

    @Autowired
    NotificationServices notificationServices;

    @Autowired
    CartServices cartServices;

    @Autowired
    WishlistServices wishlistServices;

    @Autowired
    WaitingListServices waitingListServices;


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

        Product product = productRepostory.findById(id);
        if(product!=null){
            product.setComments(commentServices.getCommentByProduct(product.getId()));
            product.setBorrows(borrowServices.getBorrowByProduct(product.getId()));
            product.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(product.getId()));
        }
        return product;
    }

    public List<Product> getProductByCategory(String category){
        List<Product> list = productRepostory.findProductsByCategory(category);
        return getProducts(list);
    }



    public List<Product> getProductByType(String type){
        List<Product> list = productRepostory.findProductsByType(type);
        return getProducts(list);
    }


    public  List<Product> getProductByName(String name){
        List<Product> list = productRepostory.findProductsByName(name);
        return getProducts(list);
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
        commentServices.deleteByProduct(id);
        notificationServices.deleteByProduct(id);
        borrowServices.deleteByProduct(id);
        requestBorrowServices.deleteByProduct(id);
        cartServices.deleteByProduct(id);
        wishlistServices.deleteByProduct(id);
        waitingListServices.deleteByProduct(id);
    }


    public List<Product> getProductsByKeyWord(String key){
        Iterable<Product> list= listProduct();
        Vector<Product> res =new Vector<Product>();
        for (Product product : list) {
            if (product.getName().toLowerCase().contains(key.toLowerCase()) || product.getCategory().toLowerCase().contains(key.toLowerCase()) || product.getType().toLowerCase().contains(key.toLowerCase()))
                res.add(product);
        }
        return res;
    }

    public List<Product> getProductByUser(long user){
        List<Product> list = productRepostory.findProductsByUser(user);
        return getProducts(list);
    }

    //Get Products
    private List<Product> getProducts(List<Product> list) {
        if(list!=null) {
            for (Product p : list) {
                p.setComments(commentServices.getCommentByProduct(p.getId()));
                p.setBorrows( borrowServices.getBorrowByProduct(p.getId()));
                p.setRequestBorrows(requestBorrowServices.getRequestBorrowByProduct(p.getId()));
            }
        }
        return list;
    }

}
