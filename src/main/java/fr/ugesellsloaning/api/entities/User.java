package fr.ugesellsloaning.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

    public User(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d = new java.util.Date();
        updatedAt = dateFormat.format(d).toString();
        NberOfTimesToBorrow = 0;

    }

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.login = user.getLogin();
        this.email =user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.updatedAt = user.getUpdatedAt();
        this.updatedBy = user.getUpdatedBy();
        this.NberOfTimesToBorrow = user.getNberOfTimesToBorrow();
        this.password = user.getPassword();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@ApiModelProperty(hidden = true)
    long id;

    @Column(unique = true)
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotBlank(message = "Login cannot be null")
    String login;

    @NotBlank(message = "Name cannot be null")
    String lastName;

    @NotBlank(message = "Firstname cannot be null")
    String firstName;

    @Column(unique = true)
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password cannot be null")
    @Size(min=6, max = 255, message = "Le mot de passe doit avoir au moins 6 caractère")
    @Column(length = 255, nullable = false)
    String password;

    @Column
    boolean isActive;

    @Column
    String phone;

    @Column
    String address;

    @Column
    String role;

    @LastModifiedDate
    @JsonIgnore
    String updatedAt;

    @LastModifiedBy
    @JsonIgnore
    String updatedBy;


    long NberOfTimesToBorrow;


    @JsonRawValue
    public int totalBorrow(){
        return borrows.size();
    }

    @JsonRawValue
    int totalCart;

    @JsonRawValue
    int totalWishlist;

    @JsonRawValue
    public int totalNotification(){
        int total=0;
        for (Notification n: notifications) {

            //Notification Not Read
            if(!n.isReadNotification()) total++;

        }
        return total;
    }



    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    //@JsonBackReference(value = "user-product")
    Collection<Product> products;


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-comment")
    Collection<Comment> comments;


    @OneToOne(cascade = {CascadeType.ALL}, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    Account account;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JsonBackReference(value = "user-notification")
    Collection<Notification> notifications;

    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    //@JsonBackReference(value = "user-borrows")
    Collection<Borrow> borrows;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-request")
    Collection<RequestBorrow> requestBorrows;

}
