package com.keita.musicbay.repository;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ConversationRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @BeforeEach
    void insert(){
        customerRepository.save(Customer.builder().username("brr").build());

        List<Conversation> conversations = Arrays.asList(
                Conversation.builder().id(1L).build()
        );
        conversations.get(0).getMessages().add(new Message(1L,"allo","brr"));
        conversations.get(0).getUsers().add(customerRepository.findByUsername("brr").get());

        conversationRepository.saveAll(conversations);
    }

    @Test
    void getByUser(){
        //ARRANGE
        User user = customerRepository.findByUsername("brr").get();

        //ACT
        List<Conversation> conversations = conversationRepository.getByUser(user);

        //ASSERT
        assertEquals(1,conversations.size());
    }
}
