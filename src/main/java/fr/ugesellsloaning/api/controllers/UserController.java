package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.security.SpringSecurityConfiguration;
import fr.ugesellsloaning.api.services.AccountServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Utilisateur \"User\""})
@RestController
//@RequestMapping("/api")
public class UserController {
    @Autowired
    UserServices userServices;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @Autowired
    AccountServices accountServices;


    @Autowired
    HttpServletRequest request;

    @Autowired
    SpringSecurityConfiguration springSecurityConfiguration;

    Principal principal;

    Authentication authentication;


    @GetMapping(path = "/api/user")
    public List<User> list(){
        return (List<User>) userServices.listUser();
    }


    @PostMapping(path = "/register")
    public boolean register(@Valid @RequestBody User user){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userServices.save(user);
    }

    @GetMapping(path = "/api/user/{id}")
    public User getById(@PathVariable(value = "id")  long id){
        return  userServices.getUserById(id);
    }

    @GetMapping(path = "/api/user/current-user")
    public Optional<User> getCurrentUser(Principal principal){
        return userServices.getByLoginQuery(principal.getName());
    }

    @PutMapping(value = "/api/user/edit/")
    public void edit(@Valid @RequestBody User user){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServices.save(user);
    }

    @DeleteMapping("/api/user/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        userServices.deleteById(id);
    }

    /*
    @PostMapping("/secured/test")
    public int logintest(@RequestBody User user){
        User user1 = userServices.getUserByEmail(user.getEmail());

        if(user1 != null){
            //String password = passwordEncoder.encode(user.getPassword());
            //System.out.println(password);
            System.out.println(user1.getPassword());
            //User currentUser = (User)request.getAttribute("userName");

            if(user.getEmail().equals(user1.getEmail()) && user.getPassword().equals(user1.getPassword())) {
                System.out.println("user current " + principal.getName());
                return (int) user1.getId();
            }
            else return -1;
        }
        return -2;
    }
*/

}
