package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.CommentServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Commentaires \"Comment\""})
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentServices commentServices;


    @Autowired
    ProductServices productServices;

    @Autowired
    UserServices userServices;

    @GetMapping(path = "/")
    public List<Comment> list(){
        return (List<Comment>) commentServices.listComment();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody  Comment comment){
        // product.setUser(user);
        //product.setImage(fileName);
        //User user = userServices.getUserByEmail(username);
        //long id=3;
        // Optional<Product> product = productServices.getProductById(id);
        //comment.setProduct(product.get());


        // One one = oneRepository(many.getOne_id()); //Get the parent Object
        //Many newMany  = new Many(); //Create a new Many object
        //newMany.setName(many.getName());
        //newMany.setOne(one); // Set the pa

        //Product product = productServices(comment.getProduct())
        String email = "kanghebalde@mail.com";
        User user = userServices.getUserByEmail(email);
        comment.setUser(user);

        commentServices.save(comment);
    }

    @GetMapping(path = "/{id}")
    public Optional<Comment> getById(@PathVariable(value = "id")  long id){ return  commentServices.getCommentById(id); }


    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody  Comment comment){
        commentServices.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        commentServices.deleteById(id);
    }
}
