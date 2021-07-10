package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Message;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SentMessage implements Serializable {
    private Long id,conversationId;
    private LocalDateTime date;
    private String content,sendBy;

    public SentMessage(){}

    public SentMessage(Message message){
        this.id = message.getId();
        this.date = message.getDate();
        this.sendBy = message.getSendBy();
        this.content = message.getContent();
        this.conversationId = message.getId();
    }
}
