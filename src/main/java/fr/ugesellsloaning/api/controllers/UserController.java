package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Utilisateur \"User\""})
@RestController
public class UserController {
    @Autowired
    UserServices userServices;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping(path = "/api/user/")
    public List<User> list(){
        return (List<User>) userServices.listUser();
    }

    @PostMapping(path = "/register")
    public void register(@Valid @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServices.save(user);
        System.out.println("la date du jour est :" + new Date());
    }

    @GetMapping(path = "/api/user/{id}")
    public Optional<User> getById(@PathVariable(value = "id")  long id){
        return  userServices.getUserById(id);
    }


    @PutMapping(value = "/api/user/")
    public void edit(@Valid @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServices.save(user);
    }

    @DeleteMapping("/api/user/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        userServices.deleteById(id);
    }
}