package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.WaitingList;
import fr.ugesellsloaning.api.services.WaitingListServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api( tags={"Operations Liste d'attente \"WaitingList\""})
@RestController
@RequestMapping("/api/WaitingList")
public class WaitingListController {

    @Autowired
    WaitingListServices waitingListServices;

    @GetMapping(path = "/{product}")
    public WaitingList getWaitingList(@PathVariable(value = "product")  long product){
        return  waitingListServices.getWaitingListByProduct(product);
    }
}
