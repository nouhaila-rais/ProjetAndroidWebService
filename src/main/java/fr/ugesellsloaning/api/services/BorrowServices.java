package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.Borrow;
import fr.ugesellsloaning.api.repositories.IBorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServices {
    @Autowired
    private IBorrowRepository borrowRepostory;

    public void save(Borrow borrow){
        borrowRepostory.save(borrow);
    }

    public Iterable<Borrow> listBorrow(){
        return borrowRepostory.findAll();
    }

    public Optional<Borrow> getBorrowById(long id){ return borrowRepostory.findById(id); }

    public List<Borrow> getBorrowByEndAt(Date date){ return borrowRepostory.findByEndAt(date); }

    public void delete(Borrow borrow){
        borrowRepostory.delete(borrow);
    }

    public void deleteById(Long id){
        borrowRepostory.deleteById(id);
    }






}
