package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface IAccountRepository extends CrudRepository<Account, Long> {
    Account findAccountByUser(long user);

}
