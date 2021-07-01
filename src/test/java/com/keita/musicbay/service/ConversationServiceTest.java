package com.keita.musicbay.service;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.enums.ConversationType;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    @Mock
    ConversationRepository conversationRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ConversationService conversationService;

    @Test
    void createConversation(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();

        Customer customer = Customer.builder().userName("brr").build();

        conversation.getUser().add(customer);

        ConversationDTO conversationDTO = new ConversationDTO(conversation);

        conversation.getUser().forEach(user -> when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(customer)));
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        //ACT
        ConversationDTO createdConversation = conversationService.createConversation(conversationDTO);

        //ARRANGE
        assertNotNull(createdConversation);
        assertEquals(1,createdConversation.getUsernames().size());
    }

}
