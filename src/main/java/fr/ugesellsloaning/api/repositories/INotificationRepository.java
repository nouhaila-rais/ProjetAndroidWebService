package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface INotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findNotificationByUser(long user);
    Notification findById(long id);
    List<Notification> findNotificationByProduct(long product);

    @Query("select n from Notification n where n.user =  :user ORDER BY n.createdAt desc ")
    List<Notification> notificationOfUser(long user);
}
