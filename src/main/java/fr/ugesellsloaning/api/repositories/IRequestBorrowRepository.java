package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import org.springframework.data.repository.CrudRepository;

public interface IRequestBorrowRepository extends CrudRepository<RequestBorrow, Long> {
   
}
