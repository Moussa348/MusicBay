package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:5001")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/postComment/{title}")
    public PostedComment postComment(@RequestBody PostedComment postedComment,@PathVariable String title){
        return commentService.postComment(postedComment,title);
    }

    @PatchMapping("/increaseLike")
    public PostedComment increaseLike(@RequestParam("id") Long id,@RequestParam("username") String username){
        return commentService.increaseLike(id,username);
    }

    @PatchMapping("/decreaseLike")
    public PostedComment decreaseLike(@RequestParam("id") Long id,@RequestParam("username") String username){
        return commentService.decreaseLike(id,username);
    }



    @GetMapping("/getListCommentOfMusic/{title}")
    public List<PostedComment> getListCommentOfMusic(@PathVariable String title){
        return commentService.getListCommentOfMusic(title);
    }

}
