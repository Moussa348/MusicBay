package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Comment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostedComment implements Serializable {
    private Long id;
    private LocalDateTime date;
    private String content,sendBy;
    private Integer nbrLike;

    public PostedComment(String content,String sendBy,Integer nbrLike){
        this.content = content;
        this.sendBy = sendBy;
        this.nbrLike = nbrLike;
    }

    public PostedComment(Comment comment){
        this.id = comment.getId();
        this.date = comment.getDate();
        this.sendBy = comment.getSendBy();
        this.content = comment.getContent();
        this.nbrLike = comment.getNbrLike();
    }
}
