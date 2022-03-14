package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    }

    @GetMapping(path = "/api/user/{id}")
    public Optional<User> getById(@PathVariable(value = "id")  long id){
        return  userServices.getUserById(id);
    }

    /*@GetMapping(path = "/login/{login}")
    public List<User> getByLogin(@PathVariable(value = "login")  String login){
        return  userServices.getUserByLogin(login);
    }*/

    @PutMapping(value = "/api/user/")
    public void edit(@Valid @RequestBody User user){
        userServices.save(user);
    }

    @DeleteMapping("/api/user/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        userServices.deleteById(id);
    }
}
