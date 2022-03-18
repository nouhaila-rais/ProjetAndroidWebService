package fr.ugesellsloaning.api.services;


import fr.ugesellsloaning.api.entities.Cart;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.Wishlist;
import fr.ugesellsloaning.api.repositories.IWishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class WishlistServices {

    @Autowired
    IWishlistRepository iWishlistRepository;

    @Autowired
    ProductServices productServices;

    @Autowired
    CartServices cartServices;

    public void save(Wishlist wishlist){
        iWishlistRepository.save(wishlist);
    }

    public Iterable<Wishlist> wishlists(){
        return iWishlistRepository.findAll();
    }

    public void delete(Wishlist wishlist){
        iWishlistRepository.delete(wishlist);
    }

    public void deleteById(Long id){
        iWishlistRepository.deleteById(id);
    }

    public void deleteByUser(long user){
        List<Wishlist> wishlists = getWishlistByUser(user);
        for (Wishlist wishlist: wishlists) {
            deleteById(wishlist.getId());
        }
    }
    public void deleteByProduct(long product){
        Wishlist wishlist = iWishlistRepository.findWishlistByProduct(product);
        if(wishlist!=null){
            iWishlistRepository.deleteById(wishlist.getId());
        }
    }

    public Optional<Wishlist> getWishlistById(long id){
        return iWishlistRepository.findById(id);
    }

    public List<Wishlist> getWishlistByUser(long user){
        return iWishlistRepository.findWishlistByUser(user);
    }

    public List<Product> getProductInWishlist(long user){
        List<Wishlist> wishlists = getWishlistByUser(user);
        Vector<Product> products = new Vector<Product>();

        for (Wishlist wishlist: wishlists) {
            products.add(productServices.getProductById(wishlist.getProduct()));
        }
        return  products;
    }

    public void addInCart(long user){
        boolean existInCart = false;
        List<Wishlist> wishlists = getWishlistByUser(user);
        List<Cart> cart = cartServices.getCartByUser(user);
        for (Wishlist wishlist: wishlists) {
            Cart cart2 = new Cart();
            cart2.setUser(user);
            cart2.setProduct(wishlist.getProduct());
            for (Cart c: cart) {
                if (c.getProduct() == cart2.getProduct() && c.getUser() == cart2.getUser()) {
                    existInCart = true;
                }
            }
            if (existInCart == false) cartServices.save(cart2);
        }
        deleteByUser(user);
    }
}
