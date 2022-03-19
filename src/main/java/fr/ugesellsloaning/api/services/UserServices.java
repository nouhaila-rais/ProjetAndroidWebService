package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    CommentServices commentServices;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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

    @Autowired
    AccountServices accountServices;


    public boolean save(User user){
        boolean userExist = false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Iterable<User> listUser = listUser();
        for (User u : listUser){
            if(u.getEmail().equals(user.getEmail())){
                userExist = true;
            }
        }
        if(userExist==false){
            userRepository.save(user);
            if(user.getRole().equals("Customer")){
                Account account = new Account();
                account.setUser(user.getId());
                accountServices.save(account);
            }

            return true;
        }
        return false;
    }

    public Iterable<User> listUser(){
        Iterable<User> listUser = userRepository.findAll();
        return  getUsers((List<User>) listUser);
    }

    public User getUserById(long id){
        User user= userRepository.findById(id);
        return getUser(user);
    }


    public Optional<User> getByLoginQuery(String login){
        return  userRepository.findByEmail(login);
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

    public void Forgotyourpassword(String email) {
        User user = getUserByEmail(email);
        if(user!=null) {
            String objet = "Mot de passe oublié";
            String message = "Bonjour,\n\nVoici votre mot de passe : " + user.getPassword() + "\n\nUniversité Gustave Eiffel";
            notificationServices.SendMailNotificationUtilisateur(user, objet, message);
        }
    }

    public void SuccesRegister(String email) {
        User user = getUserByEmail(email);
        if(user!=null) {
            String objet = "Confirmation de votre inscription";
            String message = "Bonjour," +
                    "\n\nVous venez de vous inscrire sur l'application UGE." +
                    "Nous vous souhaitons la bienvenue. \n\nVeuillez trouver ci-dessous vos identifiants de connexion." +
                    "\nVotre identifiant : " + user.getEmail() +
                    "\nVotre mot de passe : " +  user.getPassword() +
                    "\n\nCordialement."+
                    "\n\nUniversité Gustave Eiffel";
            notificationServices.SendMailNotificationUtilisateur(user, objet, message);
        }
    }
}