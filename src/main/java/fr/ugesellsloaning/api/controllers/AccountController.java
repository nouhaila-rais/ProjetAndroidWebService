package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.AccountServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api( tags={"Operations Compte \"Account\""})
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountServices accountServices;

    @Autowired
    UserServices userServices;

    @ApiOperation(value = "Récupère tous les comptes")
    @GetMapping(path = "/")
    public List<Account> list(){
        return (List<Account>) accountServices.listAccount();
    }

    @ApiOperation(value = "Crediter Mon compte")
    @PostMapping(path = "/credit/")
    public void add( @RequestBody Account account){
        String email = "kanghebalde1@gmail.com";
        User user = userServices.getUserByEmail(email);
        accountServices.creditAccount(user.getId(), account.getSolde());
    }

    @ApiOperation(value = "Récupèration d'un compte")
    @GetMapping(path = "/{id}")
    public Account getById(@PathVariable(value = "id")  long id){ return  accountServices.getAccountById(id); }

    @ApiOperation(value = "Modification du compte")
    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody Account account){
        accountServices.save(account);
    }

    @ApiOperation(value = "Suppression d'un compte")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        accountServices.deleteById(id);
    }
}
