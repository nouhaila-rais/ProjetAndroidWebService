package fr.ugesellsloaning.api.controllers.mvc;

import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductMvcController {

    @Autowired
    ProductServices productServices;

    @Autowired
    UserServices userServices;

    @GetMapping("/admin/product")
    public String index(Model model){
        model.addAttribute("products", productServices.listProduct());
        return "productList";
    }

    @GetMapping("/admin/product/new")
    public String add(Model model, Product product){
        List<User> userList = (List<User>) userServices.listUser();
        model.addAttribute("users", userList);
        model.addAttribute("product", product);
        model.addAttribute("method", "post");
        return "editFormProduct";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String edit(Model model, @PathVariable(required = true) Long id){
        Product product= productServices.getProductById(id);
        List<User> userList = (List<User>) userServices.listUser();
        model.addAttribute("users", userList);
        model.addAttribute("product", product);
        model.addAttribute("method", "put");
        return "formProduct";
    }

    @PostMapping("/admin/product/remove")
    public String remove(@RequestParam("id") Long id){
        Product product= productServices.getProductById(id);
        productServices.delete(product);

        return  "redirect:/admin/product";
    }

    @RequestMapping(value = "/admin/product/save", method = {RequestMethod.POST, RequestMethod.PUT})
    public String submit(Model model, @ModelAttribute @Valid Product product, BindingResult errors, @RequestParam("_method") String method){
        List<User> userList = (List<User>) userServices.listUser();
        model.addAttribute("users", userList);
        model.addAttribute("product", product);

        if(errors.hasErrors()){
            model.addAttribute("errors", errors.getAllErrors());
            model.addAttribute("hasError", true);
            return (method.equals("post"))?"formProduct":"editFormProduct";
        }

        product.setPath((product.getName()+product.getUser()+".jpg").toLowerCase().trim());

        productServices.save(product);
        return  "redirect:/admin/product";
    }
}
