package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductRepository extends CrudRepository<Product, Long> {
    List<Product> findProductsByName(String name);
    List<Product> findProductsByCategory(String category);

}
