package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Media;
import fr.ugesellsloaning.api.exceptions.ResourceNotFoundException;
import fr.ugesellsloaning.api.repositories.IMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServices {
    @Autowired
    private IMediaRepository mediaRepostory;

    public void save(Media media){
        mediaRepostory.save(media);
    }

    public Iterable<Media> listMedia(){
        return mediaRepostory.findAll();
    }

    public Media getMediaById(long id){

        return mediaRepostory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Media", "id", id));
    }

    public Media getMediaByFilename(String filename) throws ResourceNotFoundException{
        Media media = new Media();
        try{
             media = mediaRepostory.findMediaByFilename(filename);
        }catch (ResourceNotFoundException re){
            throw new ResourceNotFoundException("Media", "filename", filename);
        }
        return  media;
    }

    public void delete(Media media){
        mediaRepostory.delete(media);
    }

    public void deleteById(Long id){
        mediaRepostory.deleteById(id);
    }






}
