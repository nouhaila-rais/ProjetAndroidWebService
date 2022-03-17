package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IWishlistRepository extends CrudRepository<Wishlist, Long> {
    List<Wishlist> findWishlistByUser(long user);
    Wishlist  findWishlistByProduct(long product);
}
