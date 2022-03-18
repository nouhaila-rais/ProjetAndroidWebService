package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.BorrowServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Emprunt \"Borrow\""})
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    BorrowServices borrowServices;

    @Autowired
    ProductServices productServices;

    @Autowired
    UserServices userServices;

    @GetMapping(path = "/")
    public List<Borrow> list(){
        return (List<Borrow>) borrowServices.listBorrow();
    }

    @GetMapping(path = "/borrowByProduct/{id}")
    public Borrow listProduct(@PathVariable(value = "id")  long id){
        List<Borrow> bb = borrowServices.getBorrowByProduct(id);
        for (Borrow b: bb ) {
            if (!b.isReturned()){
                return b;
            }

        }
        return null;
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody  Borrow borrow){
        //String email = "nouhailarais14@mail.com";
        //User user = userServices.getUserByEmail(email);
        //borrow.setUser(user.getId());
        //borrow
        User user = userServices.getUserById(borrow.getUser());
        borrowServices.save(borrow);

        //update NbrOfTimesToBorrow
        user.setNberOfTimesToBorrow(user.getNberOfTimesToBorrow() + 1);
        userServices.save(user);


        Product p = productServices.getProductById(borrow.getProduct());
        p.setAvailable(false);
        productServices.save(p);

    }

    @GetMapping(path = "/{id}")
    public Optional<Borrow> getById(@PathVariable(value = "id")  long id){ return  borrowServices.getBorrowById(id); }

    @GetMapping(path = "/date/{date}")
    public List<Borrow> getByEndAt(@PathVariable(value = "date") Date date){ return  borrowServices.getBorrowByEndAt(date); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody  Borrow borrow){
        borrowServices.save(borrow);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        borrowServices.deleteById(id);
    }

}
