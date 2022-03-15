package fr.ugesellsloaning.api.repositories;

import fr.ugesellsloaning.api.entities.Media;
import org.springframework.data.repository.CrudRepository;

public interface IMediaRepository extends CrudRepository<Media, Long> {
   Media findMediaByFilename(String filename);
}
