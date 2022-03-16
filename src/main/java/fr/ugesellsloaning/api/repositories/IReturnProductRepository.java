package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.ReturnProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IReturnProductRepository extends CrudRepository<ReturnProduct, Long> {

    List<ReturnProduct> findReturnProductByProduct(long product);


}
