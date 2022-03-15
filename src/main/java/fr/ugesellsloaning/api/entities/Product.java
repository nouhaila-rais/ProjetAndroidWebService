package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
public class Product implements Serializable {
    public  Product(){
        createdAt = new Date();
        available = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotBlank(message = "Name cannot be null")
    String name;

    @NotBlank(message = "Category cannot be null")
    String category;

    @NotBlank(message = "Type cannot be null")
    String type;

    @NotBlank(message = "Description cannot be null")
    @Column(length = 2000)
    String description;

    @NotNull(message = "Price cannot be null")
    @Min(message = "Min Price 1 Euro", value = 1)
    double price;

    @NotBlank
    String state;

    boolean available;

    Date createdAt;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    //@JsonBackReference
    Collection<Comment> comments;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    //@JsonBackReference
    Collection<Borrow> borrows;

    public Product(long i, String nom, String catgor, String type, String description, double v, String etat, boolean b, Date date) {
        this.id = i;
        this.name = nom;
        this.category = catgor;
        this.type = type;
        this.description = description;
        this.price = v;
        this.state = etat;
        date = new Date();
        this.createdAt = date;
    }
}
