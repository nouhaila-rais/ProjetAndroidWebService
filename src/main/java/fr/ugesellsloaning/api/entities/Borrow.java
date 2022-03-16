package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Borrow implements Serializable {
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

    long product;

    long user;

    //@ManyToOne(fetch = FetchType.LAZY)
    //User user;

    @OneToOne(fetch = FetchType.LAZY)
    ReturnProduct returnProduct;
}
