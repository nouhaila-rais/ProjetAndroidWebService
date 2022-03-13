package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Notification;
import fr.ugesellsloaning.api.services.NotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    NotificationServices notificationServices;

    @GetMapping(path = "/")
    public List<Notification> list(){
        return (List<Notification>) notificationServices.listNotification();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Notification notification){
        notificationServices.save(notification);
    }

    @GetMapping(path = "/{id}")
    public Optional<Notification> getById(@PathVariable(value = "id")  long id){ return  notificationServices.getNotificationById(id); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody Notification notification){
        notificationServices.save(notification);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        notificationServices.deleteById(id);
    }
}
