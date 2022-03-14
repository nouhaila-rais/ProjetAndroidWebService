package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    private  Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(path = "/")
    public List<Product> list(){
        return (List<Product>) productServices.listProduct();
    }

    @GetMapping(path = "/current-user/")
    //@PostAuthorize("hasAuthority('ADMIN') || (returnObject != null && returnObject.getUser().getEmail() == authentication.name)")
    public List<Product> listProductOfUser(){
        String username = request.getUserPrincipal().getName();
        log.info("Load product of Current user "+username);
        return productServices.getProductOfCurrentUser(username);
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Product product){
        String username = request.getUserPrincipal().getName();
        log.info("Add product by "+username);
        User user = userServices.getUserByEmail(username);
        product.setUser(user);
        productServices.save(product);
    }

    @GetMapping(path = "/{id}")
    //@PostAuthorize("hasAuthority('ADMIN') || (returnObject != null && returnObject.getUser().getEmail() == authentication.principal)")
    public Optional<Product> getById(@PathVariable(value = "id")  long id){ return  productServices.getProductById(id); }

    @GetMapping(path = "/name/{name}")
    public List<Product> getByName(@PathVariable(value = "name")  String name){ return  productServices.getProductByName(name); }

    @GetMapping(path = "/category/{category}")
    public List<Product> getByCategory(@PathVariable(value = "category")  String category){ return  productServices.getProductByCategory(category); }

    @PutMapping(value = "/")
    //@PreAuthorize("hasAuthority('ADMIN') || (returnObject != null && returnObject.getUser().getEmail() == authentication.principal)")
    public void edit(@Valid @RequestBody Product product) throws Exception {
        String username = request.getUserPrincipal().getName();
        log.info("Edit product by "+username);
        User user = userServices.getUserByEmail(username);
        if(product.getUser() == user || user.getRole()=="admin"){
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
