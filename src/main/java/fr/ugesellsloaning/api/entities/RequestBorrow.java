package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RequestBorrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    Date askedAt;

    String status;

    @ManyToOne
    Product product;

    @ManyToOne
    User user;
}
