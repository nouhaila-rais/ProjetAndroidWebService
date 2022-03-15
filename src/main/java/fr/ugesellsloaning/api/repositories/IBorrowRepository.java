package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Borrow;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface IBorrowRepository extends CrudRepository<Borrow, Long> {
        List<Borrow> findByEndAt(Date d);
        List<Borrow> findBorrowByProduct(long product);

}
