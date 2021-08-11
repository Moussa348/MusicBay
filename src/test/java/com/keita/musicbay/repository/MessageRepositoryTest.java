package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;


    @Autowired
    ConversationRepository conversationRepository;

    @BeforeEach
    void insert(){

        List<Conversation> conversations = Arrays.asList(
                Conversation.builder().build(),
                Conversation.builder().build(),
                Conversation.builder().build()
        );

        conversations.get(2).setActive(false);
        conversationRepository.saveAll(conversations);

        List<Message> messages = Arrays.asList(
                Message.builder().content("allo").sendBy("brrr").conversation(conversationRepository.getById(1L)).build(),
                Message.builder().content("allo").sendBy("brrr").conversation(conversationRepository.getById(1L)).build()
        );

        messageRepository.saveAll(messages);
    }

    @Test
    void getAllByConversationId(){
        //ARRANGE
        long id = 1L;

        //ACT
        List<Message> messages = messageRepository.getAllByConversationId(id, PageRequest.of(0,4, Sort.by("date").descending()));

        //ASSERT
        assertEquals(2,messages.size());
    }

    @Test
    void getAllByConversationIdAndConversationActiveTrue(){
        //ARRANGE
        long id = 3L;

        //ACT
        List<Message> messages = messageRepository.getAllByConversationIdAndConversationActiveTrue(id, PageRequest.of(0,4, Sort.by("date").descending()));

        //ASSERT
        assertEquals(0,messages.size());
    }
}
