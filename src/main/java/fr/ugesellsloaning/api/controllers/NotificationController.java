package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Notification;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.NotificationServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Notification \"Notification\""})
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    NotificationServices notificationServices;

    @Autowired
    UserServices userServices;

    @GetMapping(path = "/")
    public List<Notification> list() {
        return (List<Notification>) notificationServices.listNotification();
    }

    @GetMapping(path = "/user/{user}")
    public List<Notification> list(@PathVariable(value = "user")  long user){
        return notificationServices.getNotificationOrderByCreatedAtDes(user) ;
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody Notification notification) {
        notificationServices.save(notification);
    }

    @GetMapping(path = "/updateNotification/{id}/{user}")
    public int updateNotification(@PathVariable(value = "id")  long id,@PathVariable(value = "user")  long user){
        User u = userServices.getUserById(user);


        //update Nofication Read
        Notification notification = notificationServices.getNotificationById(id);
        if (notification != null) {
            notification.setReadNotification(true);
            notificationServices.save(notification);
        } else {
            log.info("Notification Already Read or ID Not Exist");
        }
        return u.totalNotification();
    }

    @GetMapping(path = "/{id}")
    public Notification getById(@PathVariable(value = "id")  long id){ return  notificationServices.getNotificationById(id); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody Notification notification){
        notificationServices.save(notification);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        notificationServices.deleteById(id);
    }
}
