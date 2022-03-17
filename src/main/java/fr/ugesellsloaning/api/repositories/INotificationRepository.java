package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface INotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findNotificationByUser(long user);
    Notification findById(long id);

}
