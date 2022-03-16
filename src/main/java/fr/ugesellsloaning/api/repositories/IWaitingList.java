package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.WaitingList;
import org.springframework.data.repository.CrudRepository;

public interface IWaitingList extends CrudRepository<WaitingList, Long> {

    WaitingList findWaitingListByProduct(long product);
}
