package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.Notification;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.ReturnProduct;
import fr.ugesellsloaning.api.services.BorrowServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.ReturnProductServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Retour d'emprunt \"Retour\""})
@RestController
@RequestMapping("/api/return")
public class ReturnProductController {
    @Autowired
    ReturnProductServices returnProductServices;

    @Autowired
    ProductServices productServices;

    @Autowired
    BorrowServices borrowServices;

    @GetMapping(path = "/")
    public List<ReturnProduct> list(){
        return (List<ReturnProduct>) returnProductServices.listReturnProduct();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody ReturnProduct returnProduct){
        returnProductServices.save(returnProduct);


        Product p = productServices.getProductById(returnProduct.getProduct());
        p.setAvailable(true);
        productServices.save(p);

        List<Borrow> list = borrowServices.getBorrowByProduct(returnProduct.getProduct());
        for (Borrow b: list) {
            b.setReturned(true);
            borrowServices.save(b);
        }


    }

    @GetMapping(path = "/{id}")
    public Optional<ReturnProduct> getById(@PathVariable(value = "id")  long id){ return  returnProductServices.getReturnProductById(id); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody ReturnProduct returnProduct){
        returnProductServices.save(returnProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        returnProductServices.deleteById(id);
    }
}
