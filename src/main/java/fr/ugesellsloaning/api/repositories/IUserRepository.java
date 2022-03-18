package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {
    List<User> findAllByLogin(String login);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.isActive = true and (u.login = ?1 or u.email=?1)")
    Optional<User> loginQuery(String login);
    User findUserByEmail(String email);
    User findById(long id);

}
