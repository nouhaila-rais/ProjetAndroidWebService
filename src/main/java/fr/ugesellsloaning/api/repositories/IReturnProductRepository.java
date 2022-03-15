package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.ReturnProduct;
import org.springframework.data.repository.CrudRepository;

public interface IReturnProductRepository extends CrudRepository<ReturnProduct, Long> {

    ReturnProduct findReturnProductByProduct(long product);
}
