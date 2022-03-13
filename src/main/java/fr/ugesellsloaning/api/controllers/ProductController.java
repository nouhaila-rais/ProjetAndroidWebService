package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductServices productServices;

    @GetMapping(path = "/")
    public List<Product> list(){ return (List<Product>) productServices.listProduct(); }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Product product){ productServices.save(product); }

    @GetMapping(path = "/{id}")
    public Optional<Product> getById(@PathVariable(value = "id")  long id){ return  productServices.getProductById(id); }

    @GetMapping(path = "/name/{name}")
    public List<Product> getByName(@PathVariable(value = "name")  String name){ return  productServices.getProductByName(name); }

    @GetMapping(path = "/category/{category}")
    public List<Product> getByCategory(@PathVariable(value = "category")  String category){ return  productServices.getProductByCategory(category); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody Product product){ productServices.save(product); }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        productServices.deleteById(id);
    }
}
