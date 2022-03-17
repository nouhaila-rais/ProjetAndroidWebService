package fr.ugesellsloaning.api.controllers;


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

    @GetMapping(path = "/totalCart/")
    public int getTotalWislhist(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

    @PostMapping(path = "/")
    public int add(@Valid @RequestBody Wishlist wishlist){
        //current Use
        boolean exist=false;

        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);

        Product product = productServices.getProductById(wishlist.getProduct());

        wishlist.setUser(user.getId());
        wishlist.setProduct(product.getId());

        List<Wishlist> wishlists = wishlistServices.getWishlistByUser(user.getId());
        for (Wishlist wishlist1: wishlists) {
            if(wishlist.getProduct() == wishlist.getProduct() && wishlist.getUser() == wishlist.getUser()){
                exist = true;
            }
        }
        if(exist == false) wishlistServices.save(wishlist);

        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

    @GetMapping(path = "/{id}")
    public Optional<Wishlist> getById(@PathVariable(value = "id")  long id){ return  wishlistServices.getWishlistById(id); }


    @GetMapping(path = "/productInWishlist/")
    public List<Product> getByUser(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        return wishlistServices.getProductInWishlist(user.getId());
    }

    @DeleteMapping("/deleteAll/")
    public int deleteCartByUser(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        wishlistServices.deleteByUser(user.getId());

        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

    @DeleteMapping("/product/{product}")
    public int deleteProductInWishlist(@PathVariable(value = "product")  long product){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);

        wishlistServices.deleteByProduct(product);

        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

    @GetMapping(path = "/addInCart/")
    public int addWishlistInCart(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        wishlistServices.addInCart(user.getId());
        return wishlistServices.getProductInWishlist(user.getId()).size();
    }

}
