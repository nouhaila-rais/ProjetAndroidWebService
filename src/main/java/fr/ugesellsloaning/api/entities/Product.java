package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.OptionalDouble;



@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@Entity
public class Product {
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

    @NotBlank(message = "Price cannot be null")
    @NotNull
    @Min(message = "Min Price 1 Euro", value = 1)
    float price;

    @NotBlank
    String state;

    boolean available;

    Date createdAt;

    @Column(length = 500)
    String image;

    @ManyToOne
    User user;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Collection<Comment> comments;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
    Collection<Borrow> borrows;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
    Collection<RequestBorrow> requestBorrows;

    public int totalComment(){
        return comments.size();
    }

/*
    public OptionalDouble avgRate(){
        return comments.stream().mapToDouble(Comment::getRate).average();
    }

 */
}
