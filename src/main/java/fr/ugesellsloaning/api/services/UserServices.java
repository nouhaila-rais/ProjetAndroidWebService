package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Product;
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


    @Autowired
    CartServices cartServices;

    @Autowired
    WishlistServices wishlistServices;

    public void save(User user){
        userRepository.save(user);
    }

    public Iterable<User> listUser(){
        Iterable<User> listUser = userRepository.findAll();
        return  getUsers((List<User>) listUser);
    }

    public User getUserById(long id){
        User user= userRepository.findById(id);
        return getUser(user);
    }

    public List<User> getUserByLogin(String login) {
        List<User> listUsers = userRepository.findAllByLogin(login);
        return getUsers(listUsers);
    }

    public User getUserByEmail(String email){
        User user = userRepository.findUserByEmail(email);
        return getUser(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void deleteById(Long id){
        List<Product> productLists = productServices.getProductByUser(id);
        for (Product product: productLists) {
            productServices.deleteById(product.getId());
        }
        userRepository.deleteById(id);
    }

    private User getUser(User user){
        if(user!=null) {
            user.setComments(commentServices.getCommentByUser(user.getId()));
            user.setNotifications(notificationServices.getNotificationByUser(user.getId()));
            user.setBorrows(borrowServices.borrowByUser(user.getId()));
            user.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(user.getId()));
            user.setProducts(productServices.getProductByUser(user.getId()));
            user.setTotalCart(cartServices.getProductInCart(user.getId()).size());
            user.setTotalWishlist(wishlistServices.getProductInWishlist(user.getId()).size());

        }
        return user;
    }

    private List<User> getUsers(List<User> userList){
        if(userList !=null) {
            for (User u : userList) {
                u.setComments(commentServices.getCommentByUser(u.getId()));
                u.setNotifications(notificationServices.getNotificationByUser(u.getId()));
                u.setBorrows(borrowServices.borrowByUser(u.getId()));
                u.setRequestBorrows(requestBorrowServices.getRequestBorrowByUserStatusIsFalse(u.getId()));
                u.setProducts(productServices.getProductByUser(u.getId()));
                u.setTotalCart(cartServices.getProductInCart(u.getId()).size());
                u.setTotalWishlist(wishlistServices.getProductInWishlist(u.getId()).size());
            }
        }
        return userList;
    }



}