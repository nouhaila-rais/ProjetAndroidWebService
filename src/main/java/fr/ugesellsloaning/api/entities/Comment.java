package fr.ugesellsloaning.api.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Comment {
    public Comment(){
        createdAt = new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long content;
    float rate;
    Date createdAt;
    @ManyToOne
    User user;
    @ManyToOne
    Product product;
}
