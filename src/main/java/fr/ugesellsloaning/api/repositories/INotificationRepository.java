package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Notification;
import org.springframework.data.repository.CrudRepository;

public interface INotificationRepository extends CrudRepository<Notification, Long> {

}
