package fr.ugesellsloaning.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import java.sql.Date;
import java.util.Collection;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
//@ApiModel("User")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {
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
    //@JsonIgnore
    String password;

    @Column
    String phone;

    @Column
    String address;

    @Column
    String role;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;

    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-comment")
    Collection<Comment> comments;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.ALL}, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    @JsonBackReference(value = "user-account")
    Account account;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-notification")
    Collection<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-borrows")
    Collection<Borrow> borrows;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-request")
    Collection<RequestBorrow> requestBorrows;
}
