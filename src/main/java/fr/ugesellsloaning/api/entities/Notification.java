package fr.ugesellsloaning.api.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {
    public Notification(){
        createdAt=new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @NotBlank(message = "Message cannot be null")
    @Column(length = 2000)
    String message;
    Date createdAt;
    @ManyToOne
    User user;
}
