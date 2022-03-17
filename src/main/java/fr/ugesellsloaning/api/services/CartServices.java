package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Cart;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.repositories.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class CartServices {

    @Autowired
    ICartRepository iCartRepository;

    @Autowired
    ProductServices productServices;

    public void save(Cart cart){
        iCartRepository.save(cart);
    }

    public Iterable<Cart> listCart(){
        return iCartRepository.findAll();
    }

    public void delete(Cart cart){
        iCartRepository.delete(cart);
    }

    public void deleteById(Long id){
        iCartRepository.deleteById(id);
    }

    public void deleteByUser(long user){
        List<Cart> carts = getCartByUser(user);
        for (Cart cart: carts) {
            deleteById(cart.getId());
        }
    }
    public void deleteByProduct(long product){
        iCartRepository.deleteById(iCartRepository.findCartByProduct(product).getId());
    }

    public Optional<Cart> getCartById(long id){
        return iCartRepository.findById(id);
    }

    public List<Cart> getCartByUser(long user){
        return iCartRepository.findCartByUser(user);
    }

    public List<Product> getProductInCart(long user){
        List<Cart> carts = getCartByUser(user);
        Vector<Product> products = new Vector<Product>();

        for (Cart cart: carts) {
            products.add(productServices.getProductById(cart.getProduct()));
        }
        return  products;
    }
}

