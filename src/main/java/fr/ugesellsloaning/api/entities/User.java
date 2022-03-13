package fr.ugesellsloaning.api.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotBlank(message = "Login cannot be null")
    String login;

    @NotBlank(message = "Name cannot be null")
    String lastName;

    @NotBlank(message = "Firstname cannot be null")
    String firstName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password cannot be null")
    String password;

    String phone;

    String address;

    String role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    Collection<Product> products;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    Collection<Comment> comments;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, optional = true)
    @JoinColumn(nullable = true)
    Account account;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    Collection<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    Collection<Borrow> borrows;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    Collection<RequestBorrow> requestBorrows;
}
