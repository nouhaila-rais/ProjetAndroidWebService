package fr.ugesellsloaning.api.controllers;


import fr.ugesellsloaning.api.converter.Converter;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api( tags={"Operations Taux de Conversion \"Converter\""})
@RestController
@RequestMapping("/api/convert")
public class ConverterController {

    @GetMapping(path = "/{currency}")
    public double convert(@PathVariable(value = "currency")  String currency){
        Converter converter = new Converter();
        return  converter.getRateFromDevise(currency);
    }
}
