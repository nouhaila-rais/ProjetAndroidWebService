package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICartRepository extends CrudRepository<Cart, Long> {

    List<Cart> findCartByUser(long user);
    Cart  findCartByProduct(long product);


}
