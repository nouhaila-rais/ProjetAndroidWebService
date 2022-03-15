package fr.ugesellsloaning.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
public class Product implements Serializable {
    public Product() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        createdAt = dateFormat.format(d).toString();
        available = true;
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

    String createdAt;


    @Column(length = 500)
    String path;

    /*
    @OneToOne
    Media image;

     */

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    @JsonBackReference(value = "user")
    User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    // @JsonBackReference(value = "comments")
    Collection<Comment> comments;


    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    Collection<Borrow> borrows;

    @OneToMany( cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    Collection<RequestBorrow> requestBorrows;

    @JsonRawValue
    public int totalComment() {
        return comments.size();
    }
}