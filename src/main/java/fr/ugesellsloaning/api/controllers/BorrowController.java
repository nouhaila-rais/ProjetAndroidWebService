package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.services.BorrowServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    BorrowServices borrowServices;

    @GetMapping(path = "/")
    public List<Borrow> list(){
        return (List<Borrow>) borrowServices.listBorrow();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody  Borrow borrow){
        borrowServices.save(borrow);
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
