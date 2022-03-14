package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import fr.ugesellsloaning.api.entities.User;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class Comment implements Serializable {
    public Comment(){
        createdAt = new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @NotBlank
    @Column(length = 2000)
    long content;

    float rate;
    Date createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
}
