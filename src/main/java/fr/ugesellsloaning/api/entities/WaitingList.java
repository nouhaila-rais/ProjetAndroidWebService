package fr.ugesellsloaning.api.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class WaitingList implements Serializable {

    public WaitingList(long product, List<RequestBorrow>  requestBorrow){
        this.product = product;
        this.requestBorrow = requestBorrow;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long product;

    @OneToMany(fetch = FetchType.LAZY)
    List<RequestBorrow> requestBorrow;

}
