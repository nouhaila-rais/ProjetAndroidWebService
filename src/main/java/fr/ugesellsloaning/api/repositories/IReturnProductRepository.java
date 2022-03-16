package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.ReturnProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReturnProductRepository extends CrudRepository<ReturnProduct, Long> {

    List<ReturnProduct> findReturnProductByProduct(long product);
}
