package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Account implements Serializable {

    public  Account(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        editedAt = dateFormat.format(d).toString();
        solde = 0.0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    double solde;

    String editedAt;

    long user;
}
