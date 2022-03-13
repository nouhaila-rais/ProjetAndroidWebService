package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices{
    @Autowired
    private IUserRepository userRepostory;

    public void save(User user){
        userRepostory.save(user);
    }

    public Iterable<User> listUser(){
        return userRepostory.findAll();
    }

    public Optional<User> getUserById(long id){
        return userRepostory.findById(id);
    }

    public List<User> getUserByLogin(String login){
        return userRepostory.findAllByLogin(login);
    }

    public void delete(User user){
        userRepostory.delete(user);
    }

    public void deleteById(Long id){
        userRepostory.deleteById(id);
    }






}
