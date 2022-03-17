package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.exceptions.ResourceNotFoundException;
import fr.ugesellsloaning.api.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AccountServices {
    @Autowired
    private IAccountRepository accountRepostory;

    public void save(Account account){
        accountRepostory.save(account);
    }

    public Iterable<Account> listAccount(){
        return accountRepostory.findAll();
    }

    public Account getAccountById(long id){

        return accountRepostory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    public void delete(Account account){
        accountRepostory.delete(account);
    }

    public void deleteById(Long id){
        accountRepostory.deleteById(id);
    }

    public double getSolde(long user){
        return accountRepostory.findAccountByUser(user).getSolde();

    }

    public Account getAccountByUser(long user){
        return accountRepostory.findAccountByUser(user);
    }

    public boolean sufficientbalance(long user ,double amount){
        double balance = getSolde(user);
        if(amount<=balance) return true;
        return false;
    }

    public void creditAccount(long user,double amount){
        Account account = getAccountByUser(user);
        account.setSolde(account.getSolde() + amount);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        account.setEditedAt(dateFormat.format(d).toString());
        save(account);
    }

}
