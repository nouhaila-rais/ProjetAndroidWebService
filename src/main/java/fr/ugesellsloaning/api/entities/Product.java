package fr.ugesellsloaning.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
public class Product implements Serializable {
    public  Product(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        createdAt = dateFormat.format(d).toString();
        available = true;
        nmberToBorrow=0;
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

    long nmberToBorrow;

    @Column(length = 500)
    String path;

    long user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
            Collection<Comment> comments;


    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    Collection<Borrow> borrows;

    @OneToMany( cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    Collection<RequestBorrow> requestBorrows;

    @JsonRawValue
    public int totalComment(){
        return comments.size();
    }

    @JsonRawValue
    public int avgRate(){
        if( comments.size()>0){
            // Double rate = comments.stream().mapToDouble(Comment::getRate).average().getAsDouble();
            return ConvertRate(comments.stream().mapToDouble(Comment::getRate).average().getAsDouble());
        }else{
            return 0;
        }
    }

    int ConvertRate(double rate) {
        double rate10 = rate*10;
        int rate2 =(int)rate;
        int intRate = rate2*10;

        int intRate10 = (int)rate10;
        int diff = intRate10 - intRate;

        if(diff==5) return intRate10;
        else if(diff<5) return intRate;
        else return intRate+5;
    }

}
