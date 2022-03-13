package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentServices commentServices;

    @GetMapping(path = "/")
    public List<Comment> list(){
        return (List<Comment>) commentServices.listComment();
    }

    @PostMapping(path = "/")
    public void add(@Valid @RequestBody  Comment comment){
        commentServices.save(comment);
    }

    @GetMapping(path = "/{id}")
    public Optional<Comment> getById(@PathVariable(value = "id")  long id){ return  commentServices.getCommentById(id); }

    @GetMapping(path = "/product/{product}")
    public List<Comment> getByLogin(@PathVariable(value = "product") Product product){
        return  commentServices.getCommentByProduct(product);
    }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody  Comment comment){
        commentServices.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        commentServices.deleteById(id);
    }
}
