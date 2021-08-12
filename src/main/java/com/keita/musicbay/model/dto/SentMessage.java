package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.enums.ConversationType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SentMessage implements Serializable {
    private Long id,conversationId;
    private LocalDateTime date;
    private ConversationType conversationType;
    private String content,sendBy,conversationName;

    public SentMessage(){}

    public SentMessage(Message message){
        this.id = message.getId();
        this.date = message.getDate();
        this.sendBy = message.getSendBy();
        this.content = message.getContent();
        this.conversationType = message.getConversation().getConversationType();
        this.conversationId = message.getConversation().getId();
        this.conversationName = conversationType.equals(ConversationType.GROUP)? message.getConversation().getName():"";
    }
}
