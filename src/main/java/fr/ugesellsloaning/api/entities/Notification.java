package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Notification implements Serializable {
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
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
}
