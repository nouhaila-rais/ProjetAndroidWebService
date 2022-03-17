package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {
    List<Product> findProductsByName(String name);

    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByType(String type);
    List<Product> findProductsByUser(long user);
    Product findById(long id);
}
