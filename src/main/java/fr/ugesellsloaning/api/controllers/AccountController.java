package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountServices accountServices;

    @GetMapping(path = "/")
    public List<Account> list(){
        return (List<Account>) accountServices.listAccount();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Account account){
        accountServices.save(account);
    }

    @GetMapping(path = "/{id}")
    public Optional<Account> getById(@PathVariable(value = "id")  long id){ return  accountServices.getAccountById(id); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody Account account){
        accountServices.save(account);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        accountServices.deleteById(id);
    }
}
