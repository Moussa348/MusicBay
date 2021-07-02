package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/postComment/{title}")
    public PostedComment postComment(@RequestBody PostedComment postedComment,@PathVariable String title){
        return commentService.postComment(postedComment,title);
    }

    @PatchMapping("/increaseLike/{id}")
    public PostedComment increaseLike(@PathVariable Long id){
        return commentService.increaseLike(id);
    }

    @GetMapping("/getListCommentOfMusic/{title}")
    public List<PostedComment> getListCommentOfMusic(@PathVariable String title){
        return commentService.getListCommentOfMusic(title);
    }

}
