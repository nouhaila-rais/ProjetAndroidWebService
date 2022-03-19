package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Comment;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.CommentServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    HttpServletRequest request;

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
        User user = userServices.getUserById(comment.getUser());
        comment.setLastName(user.getLastName());
        comment.setFirstName(user.getFirstName());

        commentServices.save(comment);

    }

    @GetMapping(path = "/{id}")
    public Optional<Comment> getById(@PathVariable(value = "id")  long id){ return  commentServices.getCommentById(id); }

    @GetMapping(path = "/user/{user}")
    public List<Comment> getByUser(@PathVariable(value = "user")  long user){ return  commentServices.getCommentByUser(user); }

    @PutMapping(value = "/")
    public void edit(@Valid @RequestBody  Comment comment){
        commentServices.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id")  long id){
        commentServices.deleteById(id);
    }
}
