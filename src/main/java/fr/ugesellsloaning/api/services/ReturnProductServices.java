package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.ReturnProduct;
import fr.ugesellsloaning.api.repositories.IReturnProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnProductServices {

    @Autowired
    IReturnProductRepository iReturnProductRepository;


    public void save(ReturnProduct returnProduct){ iReturnProductRepository.save(returnProduct); }

    public Iterable<ReturnProduct> listReturnProduct(){
        return iReturnProductRepository.findAll();
    }

    public Optional<ReturnProduct> getReturnProductById(long id){
        return iReturnProductRepository.findById(id);
    }

    public void delete(ReturnProduct returnProduct){
        iReturnProductRepository.delete(returnProduct);
    }

    public void deleteById(Long id){
        iReturnProductRepository.deleteById(id);
    }

    public List<ReturnProduct> getReturnProductByProduct(long product){
        return iReturnProductRepository.findReturnProductByProduct(product);
    }

}
