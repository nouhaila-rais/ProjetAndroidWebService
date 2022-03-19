package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.*;
import fr.ugesellsloaning.api.repositories.IWaitingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Vector;

@Service
public class WaitingListServices {
    @Autowired
    private IWaitingList iWaitingList;

    @Autowired
    UserServices userServices;

    @Autowired
    RequestBorrowServices requestBorrowServices;

    @Autowired
    ProductServices productServices;

    @Autowired
    NotificationServices notificationServices;

    public void save(WaitingList waitingList){
        iWaitingList.save(waitingList);
    }

    public Iterable<WaitingList> WaitingList(){
        Iterable<WaitingList> l = iWaitingList.findAll();
        for (WaitingList w: l) {
            w.setRequestBorrow(requestBorrowServices.getRequestBorrowByProduct(w.getProduct()));
        }
        return l;
    }

    public Optional<WaitingList> getWaitingListById(long id){
        return iWaitingList.findById(id);
    }

    public WaitingList getWaitingListByProduct(long product){
        WaitingList waitingList = new WaitingList(product, requestBorrowServices.getRequestBorrowNoTTraitedByidProduit(product));
        return waitingList;
    }


    public User UserPioritary(WaitingList waitingList){

        Vector<User> listUtilisateur =new Vector<User>();

        for (RequestBorrow requestBorrow : waitingList.getRequestBorrow()) {
            listUtilisateur.add(userServices.getUserById(requestBorrow.getUser()));
        }
        if(listUtilisateur.size()!=0) {
            User prioritaire = listUtilisateur.get(0);

            for (int i = 1; i < listUtilisateur.size(); i++) {
                User current = listUtilisateur.get(i);
                if(prioritaire.getRole().equals(current.getRole())) {

                    if (prioritaire.getNberOfTimesToBorrow()>current.getNberOfTimesToBorrow()) prioritaire=current;
                    else if (prioritaire.getNberOfTimesToBorrow()==current.getNberOfTimesToBorrow()) {
                        RequestBorrow requestPriority = requestBorrowServices.getResquestBorrowByProductAndUser(waitingList.getProduct(), prioritaire.getId());
                        RequestBorrow currentRequest = requestBorrowServices.getResquestBorrowByProductAndUser(requestPriority.getProduct(), current.getId());
                        if(Date1BeforeDate2(requestPriority.getAskedAt(),  currentRequest.getAskedAt())==false) prioritaire=current;
                    }
                }
                else if (current.getRole().equals("enseignant")) prioritaire=current; ;
            }

            return prioritaire;
        }
        return null;
    }

    public boolean Date1BeforeDate2(String date1, String date2) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime d1 = LocalDateTime.parse(date1, format);
        LocalDateTime d2 = LocalDateTime.parse(date2, format);
        if(d1.isBefore(d2)) return true;
        return false;

    }

    public void WaitingListTraitement(long product) {
        WaitingList la =  getWaitingListByProduct(product);
        User u = UserPioritary(la);
        if(u!=null) {
            Product p = productServices.getProductById(product);
            String date = requestBorrowServices.getResquestBorrowByProductAndUser(product, u.getId()).getAskedAt();

            String notification = "Le produit "+p.getName() +" est actuellement disponible vous pouvez l\'emprunter dès maintentant";
            Notification n =  new Notification();
            n.setMessage(notification);
            n.setUser(u.getId());
            n.setProduct(p.getId());
            n.setImage(p.getPath());
            notificationServices.save(n);
            String message ="Bonjour ,\n\nVous avez demander d\'emprunter le produit : "+p.getName()+" à "+date+ ", il est actuellement disponible vous pouvez l\'emprunter dès maintentant.\n Cordialement.\nUniversité Gustave Eiffel";
            String object = "Produit "+ p.getName()+" est disponible !";
            notificationServices.SendMailNotificationUtilisateur(u, object, message);

            RequestBorrow requestBorrow= requestBorrowServices.getResquestBorrowByProductAndUser(product, u.getId());
            requestBorrow.setStatus(true);
            requestBorrowServices.save(requestBorrow);
        }

    }
    public void deleteByProduct(long product){
        WaitingList waitingList =  getWaitingListByProduct(product);
        if(waitingList!=null){
            iWaitingList.deleteById(waitingList.getId());
        }
    }


}
