package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.RequestBorrow;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRequestBorrowRepository extends CrudRepository<RequestBorrow, Long> {
    List<RequestBorrow> findRequestBorrowByProduct(long product);
    List<RequestBorrow> findRequestBorrowByuser(long user);
    RequestBorrow findRequestBorrowByProductAndUser(long product, long user);

    @Query("select b from RequestBorrow b where b.user =  :user AND b.status=false ")
    List<RequestBorrow> requestBorrowByUser(@Param("user") long user);
}
