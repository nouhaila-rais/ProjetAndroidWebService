package fr.ugesellsloaning.api.controllers;


import fr.ugesellsloaning.api.entities.Cart;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.entities.Wishlist;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import fr.ugesellsloaning.api.services.WishlistServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Wishlist \"Wishlist\""})
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    WishlistServices wishlistServices;

    @Autowired
    UserServices userServices;

    @Autowired
    ProductServices productServices;

    @GetMapping(path = "/")
    public Iterable<Wishlist> list(){
        return wishlistServices.wishlists();
    }

    @GetMapping(path = "/totalWihslist/")
    public int getTotalWislhist(){
        String email =  "mounas2@gmail.com";
        User user = userServices.getUserByEmail(email);
        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

    @GetMapping(path  = "add/{product}/{user}")
    public int add(@PathVariable(value = "product")  long product,@PathVariable(value = "user")  long user){
        //current Use
        boolean exist=false;

        //String email = "fati2@gmail.com";
        User u = userServices.getUserById(user);
        Product product1 = productServices.getProductById(product);
        if (product1 != null) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(u.getId());
            wishlist.setProduct(product);

            List<Wishlist> wishlists = wishlistServices.getWishlistByUser(u.getId());
            for (Wishlist wishlist1 : wishlists) {
                if (wishlist1.getProduct() == wishlist.getProduct() && wishlist1.getUser() == wishlist.getUser()) {
                    exist = true;
                }
            }
            if (exist == false) wishlistServices.save(wishlist);
        }


        return wishlistServices.getProductInWishlist(u.getId()).size();
    }

    @GetMapping(path = "/{id}")
    public Optional<Wishlist> getById(@PathVariable(value = "id")  long id){ return  wishlistServices.getWishlistById(id); }


    @GetMapping(path = "/productInWishlist/{user}")
    public List<Product> getByUser(@PathVariable(value = "user")  long user){
        //String email = "mounas2@gmail.com";
        User u = userServices.getUserById(user);
        if(u!=null) return wishlistServices.getProductInWishlist(u.getId());
        return null;
    }

    @GetMapping("/deleteAll/{user}")
    public int deleteCartByUser(@PathVariable(value = "user")  long user){
        User u = userServices.getUserById(user);
        if(u!=null){
            wishlistServices.deleteByUser(u.getId());
            return wishlistServices.getProductInWishlist(u.getId()).size();
        }
        return 0;
    }

    @DeleteMapping("/product/{product}")
    public int deleteProductInWishlist(@PathVariable(value = "product")  long product){
        String email = "mounas2@gmail.com";
        User user = userServices.getUserByEmail(email);

        wishlistServices.deleteByProduct(product);

        return wishlistServices.getProductInWishlist(user.getId()).size();
    }
    @GetMapping(path = "/addInCart/{user}")
    public int addWishlistInCart(@PathVariable(value = "user")  long user){
        //String email ="mounas2@gmail.com";
        User u = userServices.getUserById(user);
        if(u!=null){
            wishlistServices.addInCart(u.getId());
            return wishlistServices.getProductInWishlist(u.getId()).size();
        }
        return 0;
    }

}
