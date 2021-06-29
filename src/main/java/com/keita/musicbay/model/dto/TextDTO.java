package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Comment;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TextDTO implements Serializable {
    private Long id;
    private String content,sendBy,musicTitle;
    private Integer nbrLike;
    private List<String> usernames;

    public TextDTO(){}

    public TextDTO(String content,String sendBy,List<String> usernames){
        this.content = content;
        this.sendBy = sendBy;
        this.usernames = usernames;
    }

    public TextDTO(String content,String sendBy,String musicTitle,Integer nbrLike){
        this.content = content;
        this.sendBy = sendBy;
        this.musicTitle = musicTitle;
        this.nbrLike = nbrLike;
    }


    public TextDTO(Message message){
        this.id = message.getId();
        this.sendBy = message.getSendBy();
        this.content = message.getContent();
        this.usernames = message.getUsers().stream().map(User::getUserName).collect(Collectors.toList());
    }

    public TextDTO(Comment comment){
        this.id = comment.getId();
        this.sendBy = comment.getSendBy();
        this.content = comment.getContent();
        this.musicTitle = comment.getMusic().getTitle();
        this.nbrLike = comment.getNbrLike();
    }
}
