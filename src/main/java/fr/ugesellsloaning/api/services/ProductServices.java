package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.repositories.IProductRepository;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    @Autowired
    private IProductRepository productRepostory;

    @Autowired
    private IUserRepository userRepostory;

    //SecurityUtils securityUtils = new SecurityUtils();

    public void save(Product product){
        productRepostory.save(product);
    }

    public Iterable<Product> listProduct(){
        return productRepostory.findAll();
    }

    public Optional<Product> getProductById(long id){ return productRepostory.findById(id); }

    public List<Product> getProductByCategory(String category){ return productRepostory.findProductsByCategory(category);}

    public  List<Product> getProductByName(String name){ return productRepostory.findProductsByName(name);}

    public List<Product> getProductOfCurrentUser(String username){
        return productRepostory.findAllForCurrentUser(username);
    }

    public void delete(Product product){
        productRepostory.delete(product);
    }

    public void deleteById(Long id){
        productRepostory.deleteById(id);
    }






}
