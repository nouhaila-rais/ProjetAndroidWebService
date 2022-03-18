package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
//import fr.ugesellsloaning.api.services.FileService;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

@Api( tags={"Operations Produits \"Product\""})
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductServices productServices;

    @Autowired
    UserServices userServices;

    @Autowired
    HttpServletRequest request;

    //private Product p;

    private Principal principal;
    private String email;

    private  Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(path = "/")
    public List<Product> list(){

        return (List<Product>) productServices.listProduct();

    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Product product, Principal principal) {

        Optional<User> user = userServices.getByLoginQuery(principal.getName());
        user.ifPresent(value -> product.setUser(value.getId()));

        productServices.save(product);
    }

    @GetMapping(path = "/{id}")
    //@PostAuthorize("hasAuthority('ADMIN') || (returnObject != null && returnObject.getUser().getEmail() == authentication.principal)")
    public Product getById(@PathVariable(value = "id")  long id){
        return productServices.getProductById(id);
        //return new Product(new Long(23), "nom", "catgory", "type", "description", 22.2, "Etat", true, new Date());
    }

    @GetMapping(path = "/name/{name}")
    public List<Product> getByName(@PathVariable(value = "name")  String name){ return  productServices.getProductByName(name); }

    @GetMapping(path = "/key/{key}")
    public List<Product> getByKey(@PathVariable(value = "key")  String key){
        return  productServices.getProductsByKeyWord(key.replace("%", " "));
    }


    @GetMapping(path = "/category/{category}")
    public List<Product> getByCategory(@PathVariable(value = "category")  String category){ return  productServices.getProductByCategory(category); }

    @GetMapping(path = "/type/{type}")
    public List<Product> getByType(@PathVariable(value = "type")  String type){ return  productServices.getProductByType(type); }

    @PutMapping(value = "/")
    //@PreAuthorize("hasAuthority('ADMIN') || (returnObject != null && returnObject.getUser().getEmail() == authentication.principal)")
    public void edit(@Valid @RequestBody Product product) throws Exception {
        String email = "mounas@gmail.com";
        User user = userServices.getUserByEmail(email);

        if(product.getUser() == user.getId()){
            productServices.save(product);
        }else{
            throw new Exception("Erreur vous devez etre admin ou proprietaire");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        productServices.deleteById(id);
    }


}
