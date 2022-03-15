package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IRequestBorrowRepository;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestBorrowServices {

    @Autowired
    private IRequestBorrowRepository requestBorrowRepository;

    public void save(RequestBorrow requestBorrow){ requestBorrowRepository.save(requestBorrow); }

    public Iterable<RequestBorrow> listRequestBorrow(){
        return requestBorrowRepository.findAll();
    }

    public Optional<RequestBorrow> getRequestBorrowById(long id){
        return requestBorrowRepository.findById(id);
    }

    public void delete(RequestBorrow requestBorrow){
        requestBorrowRepository.delete(requestBorrow);
    }

    public void deleteById(Long id){
        requestBorrowRepository.deleteById(id);
    }

    public List<RequestBorrow> getRequestBorrowByProduct(long product){
        return requestBorrowRepository.findRequestBorrowByProduct(product);
    }






}
