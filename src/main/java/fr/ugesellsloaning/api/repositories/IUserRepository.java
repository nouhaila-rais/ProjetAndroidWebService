package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {
    List<User> findAllByLogin(String login);
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    User findById(long id);

}
