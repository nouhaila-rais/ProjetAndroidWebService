package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServices userServices;

    @GetMapping(path = "/")
    public List<User> list(){
        return (List<User>) userServices.listUser();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody User user){
        userServices.save(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getById(@PathVariable(value = "id")  long id){
        return  userServices.getUserById(id);
    }

    @GetMapping(path = "/login/{login}")
    public List<User> getByLogin(@PathVariable(value = "login")  String login){
        return  userServices.getUserByLogin(login);
    }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody User user){
        userServices.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        userServices.deleteById(id);
    }
}
