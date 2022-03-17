package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Cart;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.CartServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Cart \"Cart\""})
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartServices cartServices;

    @Autowired
    UserServices userServices;

    @Autowired
    ProductServices  productServices;

    @GetMapping(path = "/")
    public Iterable<Cart> list(){
        return cartServices.listCart();
    }

    @GetMapping(path = "/totalCart/")
    public int getTotalCart(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        return cartServices.getProductInCart(user.getId()).size();
    }

    @PostMapping(path = "/")
    public int add(@Valid @RequestBody  Cart cart){
        //current Use
        boolean exist=false;

        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);

        Product product = productServices.getProductById(cart.getProduct());

        cart.setUser(user.getId());
        cart.setProduct(product.getId());

        List<Cart> carts = cartServices.getCartByUser(user.getId());
        for (Cart cart1: carts) {
            if(cart1.getProduct() == cart.getProduct() && cart1.getUser() == cart.getUser()){
                exist = true;
            }
        }
        if(exist == false) cartServices.save(cart);

        return cartServices.getProductInCart(user.getId()).size();
    }

    @GetMapping(path = "/{id}")
    public Optional<Cart> getById(@PathVariable(value = "id")  long id){ return  cartServices.getCartById(id); }


    @GetMapping(path = "/productInCart/")
    public List<Product> getByUser(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        return cartServices.getProductInCart(user.getId());
    }

    @DeleteMapping("/deleteAll/")
    public int deleteCartByUser(){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);
        cartServices.deleteByUser(user.getId());

        return cartServices.getProductInCart(user.getId()).size();
    }

    @DeleteMapping("/product/{product}")
    public int deleteCartByProduct(@PathVariable(value = "product")  long product){
        String email = "nouhailarais14@gmail.com";
        User user = userServices.getUserByEmail(email);

        cartServices.deleteByProduct(product);

        return cartServices.getProductInCart(user.getId()).size();
    }


}