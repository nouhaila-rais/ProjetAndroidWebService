package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.RequestBorrow;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IRequestBorrowRepository;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

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

    public List<RequestBorrow> getRequestBorrowByUser(long user){
        return requestBorrowRepository.findRequestBorrowByuser(user);
    }

    public RequestBorrow getResquestBorrowByProductAndUser(long product, long user){

        List<RequestBorrow> list = getRequestBorrowByUser(user);

        for (RequestBorrow requestBorrow : list) {
            if( requestBorrow.getProduct() ==product && !requestBorrow.isStatus())
                return requestBorrow;
        }
        return null;
    }

    public List<RequestBorrow>  getRequestBorrowNoTTraitedByidProduit(long product) {
        List<RequestBorrow> list = getRequestBorrowByProduct(product);
        Vector<RequestBorrow> res = new Vector<RequestBorrow>();
        for (RequestBorrow requestBorrow : list) {
            if(!requestBorrow.isStatus()) res.add(requestBorrow);
        }
        return res;
    }

    public List<RequestBorrow> getRequestBorrowByUserStatusIsFalse(long user){
        return requestBorrowRepository.requestBorrowByUser(user);
    }
}
