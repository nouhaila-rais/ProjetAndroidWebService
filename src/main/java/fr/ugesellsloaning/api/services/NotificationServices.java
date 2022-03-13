package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Notification;
import fr.ugesellsloaning.api.repositories.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServices {
    @Autowired
    private INotificationRepository notificationRepository;

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






}
