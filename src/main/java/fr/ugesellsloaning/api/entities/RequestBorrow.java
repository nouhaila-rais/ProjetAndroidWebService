package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class RequestBorrow implements Serializable {
    public RequestBorrow(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        askedAt = dateFormat.format(d).toString();
        status=false;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String askedAt;

    boolean status;

    String startAt;

    String endAt;

    long product;

    long user;

}
