package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.model.enums.ConversationType;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.MessageRepository;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    @Mock
    ConversationRepository conversationRepository;

    @Mock
    MessageRepository messageRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ConversationService conversationService;

    @Test
    void createConversation(){
        //ARRANGE
        ConversationDTO conversationDTO = ConversationDTO.builder().createdBy("brr").name("glowGang").conversationType(ConversationType.GROUP).usernames(Arrays.asList("brr","bigBrr")).build();
        List<User> users = Arrays.asList(
                Customer.builder().username("brr").build(),
                Customer.builder().username("bigBrr").build()
        );
        Conversation conversation = new Conversation(conversationDTO,users);
        conversation.setId(1L);

        conversationDTO.getUsernames().forEach(username -> when(userRepository.findByUsername(username)).thenReturn(Optional.of(users.get(0))).thenReturn(Optional.of(users.get(1))));

        //ACT
        ConversationDTO createdConversation = conversationService.createConversation(conversationDTO);

        //ARRANGE
        assertNotNull(createdConversation);
    }

    @Test
    void addUserInConversationGroup(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();
        User userToAdd = Customer.builder().username("brr").build();

        conversation.getUsers().add(Customer.builder().username("bigBrr").build());


        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);
        when(userRepository.findByUsername(userToAdd.getUsername())).thenReturn(Optional.of(userToAdd));
        //ACT
        conversationService.addUserInConversationGroup(conversation.getId(),userToAdd.getUsername());

        //ASSERT
        assertEquals(2,conversation.getUsers().size());
    }

    @Test
    void removeUserFromConversationGroup(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();
        User userToRemove = Customer.builder().username("brr").build();

        conversation.getUsers().add(Customer.builder().username("bigBrr").build());
        conversation.getUsers().add(userToRemove);

        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);
        //ACT
        conversationService.removeUserFromConversationGroup(conversation.getId(),userToRemove.getUsername());

        //ASSERT
        assertEquals(1,conversation.getUsers().size());
    }

    @Test
    void sendMessageInConversation(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();
        SentMessage sentMessage = new SentMessage(Message.builder().content("sadasd").conversation(conversation).build());


        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);

        //ACT
        SentMessage newSentMessage = conversationService.sendMessageInConversation(sentMessage);

        //ASSERT
        assertNotNull(newSentMessage);
        assertEquals(1,conversation.getMessages().size());
    }

    @Test
    void deleteConversation(){
        //ARRANGE
        Conversation conversation = Conversation.builder().id(1L).build();
        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);

        //ACT
        conversationService.deleteConversation(conversation.getId());

        //ASSERT
        assertFalse(conversation.isActive());

    }

    @Test
    void getMessagesFromConversation(){
        //ARRANGE
        Long id = 1L;
        int noPage = 0;
        List<Message> messages = Arrays.asList(
                Message.builder().content("allo").sendBy("brrr").conversation(Conversation.builder().id(1L).build()).build(),
                Message.builder().content("allo").sendBy("brrr").conversation(Conversation.builder().id(1L).build()).build()
        );
        when(messageRepository.getAllByConversationIdAndConversationActiveTrue(id, PageRequest.of(noPage,20, Sort.by("date").descending()))).thenReturn(messages);

        //ACT
        List<SentMessage> sentMessagesInConversation = conversationService.getMessagesFromConversation(id,noPage);

        //ASSERT
        assertEquals(2,sentMessagesInConversation.size());
    }

    @Test
    void getLastSentMessages(){
        //ARRANGE
        User user = Customer.builder().username("bigBrr").build();
        int noPage = 0;
        List<Conversation> conversations = Arrays.asList(
                Conversation.builder().id(1L).build()
        );
        conversations.get(0).getMessages().add(Message.builder().conversation(conversations.get(0)).build());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(conversationRepository.getByUserAndConversationTrue(user,PageRequest.of(noPage,20))).thenReturn(conversations);

        //ACT
        List<SentMessage> lastSentMessages = conversationService.getLastSentMessages(user.getUsername(),noPage);

        //ASSERT
        assertEquals(1,lastSentMessages.size());
    }
}
