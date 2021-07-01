package com.keita.musicbay.model;

import com.keita.musicbay.model.dto.SentMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends Text implements Serializable {

    @ManyToOne
    private Conversation conversation;

    public Message(){}

    public Message(Long id, String content, String sendBy) {
        super(id, content, sendBy);
    }

    public Message (SentMessage sentMessage, Conversation conversation){
        super(sentMessage.getId(),sentMessage.getContent(),sentMessage.getSendBy());
        this.conversation = conversation;
    }
}
