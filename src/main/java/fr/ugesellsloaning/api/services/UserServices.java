package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.entities.WaitingList;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class UserServices{
    @Autowired
    private IUserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public Iterable<User> listUser(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id);
    }

    public List<User> getUserByLogin(String login){
        return userRepository.findAllByLogin(login);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

}