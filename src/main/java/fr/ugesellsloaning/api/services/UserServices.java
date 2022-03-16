package fr.ugesellsloaning.api.services;


import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    CommentServices commentServices;

    @Autowired
    NotificationServices notificationServices;

    @Autowired
    BorrowServices borrowServices;

    @Autowired
    RequestBorrowServices requestBorrowServices;

    @Autowired
    ProductServices productServices;

    public void save(User user){
        userRepository.save(user);
    }

    public Iterable<User> listUser(){
        Iterable<User> listUser = userRepository.findAll();
        for (User u : listUser) {
            u.setComments(commentServices.getCommentByUser(u.getId()));
            u.setNotifications(notificationServices.getNotificationByUser(u.getId()));
            u.setBorrows(borrowServices.borrowByUser(u.getId()));
            u.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(u.getId()));
            u.setProducts(productServices.getProductByUser(u.getId()));
        }

        return listUser;
    }

    public User getUserById(long id){

        User u = userRepository.findById(id);
        if(u!=null){
            u.setComments(commentServices.getCommentByUser(u.getId()));
            u.setNotifications(notificationServices.getNotificationByUser(u.getId()));
            u.setBorrows(borrowServices.borrowByUser(u.getId()));
            u.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(u.getId()));
            u.setProducts(productServices.getProductByUser(u.getId()));
        }
        return u;
    }

    public List<User> getUserByLogin(String login) {
        List<User> listUser = userRepository.findAllByLogin(login);
        if(listUser!=null){
            for (User u : listUser) {
                u.setComments(commentServices.getCommentByUser(u.getId()));
                u.setNotifications(notificationServices.getNotificationByUser(u.getId()));
                u.setBorrows(borrowServices.borrowByUser(u.getId()));
                u.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(u.getId()));
                u.setProducts(productServices.getProductByUser(u.getId()));
            }
        }
        return listUser;
    }

    public User getUserByEmail(String email){
        User u = userRepository.findByEmail(email);
        if(u!=null) {
            u.setComments(commentServices.getCommentByUser(u.getId()));
            u.setNotifications(notificationServices.getNotificationByUser(u.getId()));
            u.setBorrows(borrowServices.borrowByUser(u.getId()));
            u.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(u.getId()));
            u.setProducts(productServices.getProductByUser(u.getId()));
        }
        return u;
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }



}