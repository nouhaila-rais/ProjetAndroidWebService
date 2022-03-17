package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Notification implements Serializable {
    public Notification(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        createdAt = dateFormat.format(d).toString();
        readNotification = false;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @NotBlank(message = "Message cannot be null")
    @Column(length = 2000)

    String message;

    String createdAt;

    long user;

    long product;

    String image;

    boolean readNotification;

}
