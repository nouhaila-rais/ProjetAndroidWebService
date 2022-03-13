package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICommentRepository extends CrudRepository<Comment, Long> {
        List<Comment> findCommentByProduct(Product product);
}
