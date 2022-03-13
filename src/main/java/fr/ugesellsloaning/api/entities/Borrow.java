package fr.ugesellsloaning.api.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Borrow {
    public Borrow(){
        startAt = new Date();
        returned = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    Date startAt;

    Date endAt;

    boolean returned;

    @ManyToOne
    Product product;

    @ManyToOne
    User user;
}
