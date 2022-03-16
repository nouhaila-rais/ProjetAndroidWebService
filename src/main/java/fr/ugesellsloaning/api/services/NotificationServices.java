package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Notification;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class NotificationServices {
    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    UserServices userServices;

    public void save(Notification notification){
        notificationRepository.save(notification);
    }

    public Iterable<Notification> listNotification(){
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(long id){
        return notificationRepository.findById(id);
    }

    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    public void deleteById(Long id){
        notificationRepository.deleteById(id);
    }


    public void SendMailNotificationUtilisateur(User user,String object, String message) {
        //User u = userServices.getUserById(u)
        //Utilisateur u = utilisateurDao.GetUtilisateurById(IdUtilisateur);
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.makcenter.ma");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("nouhaila@makcenter.ma","Nouhaila14@");
                    }
                });

        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("Nouhaila@makcenter.ma","Université Gustave Eiffel"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            msg.setSubject(object);
            msg.setText(message);
            Transport.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public List<Notification> getNotificationByUser(long user){
        return notificationRepository.findNotificationByUser(user);
    }

}
