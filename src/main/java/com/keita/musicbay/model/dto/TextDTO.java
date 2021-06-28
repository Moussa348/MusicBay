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
    private String content;
    private Integer nbrLike;
    private List<String> usernames;

    public TextDTO(){}

    public TextDTO(String content,List<String> usernames){
        this.content = content;
        this.usernames = usernames;
    }

    public TextDTO(Message message){
        this.id = message.getId();
        this.content = message.getContent();
        this.usernames = message.getUsers().stream().map(User::getUserName).collect(Collectors.toList());
    }

    public TextDTO(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nbrLike = comment.getNbrLike();
    }
}
