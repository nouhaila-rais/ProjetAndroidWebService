package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IBorrowRepository extends CrudRepository<Borrow, Long> {
        List<Borrow> findByEndAt(Date d);
        List<Borrow> findBorrowByProduct(long product);
        List<Borrow> findBorrowByUser(long user);

        @Query("select b from Borrow b where b.product =  :product AND b.returned=false ")
        Borrow borrowIsFalse(@Param("product") long product);

        @Query("select b from Borrow b where b.user =  :user AND b.returned=false ")
        List<Borrow> borrowByUser(@Param("user") long user);



}
