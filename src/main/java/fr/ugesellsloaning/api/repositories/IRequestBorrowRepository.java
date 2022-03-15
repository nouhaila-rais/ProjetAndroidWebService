package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRequestBorrowRepository extends CrudRepository<RequestBorrow, Long> {
    List<RequestBorrow> findRequestBorrowByProduct(long product);
   
}
