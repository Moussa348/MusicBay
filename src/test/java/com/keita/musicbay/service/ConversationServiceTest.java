package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.model.enums.ConversationType;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        User user = Customer.builder().username("bigBrr").build();
        User userToAdd = Customer.builder().username("brr").build();

        conversation.getUsers().add(user);

        when(userRepository.findByUsername(userToAdd.getUsername())).thenReturn(Optional.of(userToAdd));
        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        //ACT
        ConversationDTO conversationDTO = conversationService.addUserInConversationGroup(conversation.getId(),userToAdd.getUsername());

        //ASSERT
        assertNotNull(conversationDTO);
        assertEquals(2,conversationDTO.getUsernames().size());
    }

    @Test
    void removeUserFromConversationGroup(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();
        User user = Customer.builder().username("bigBrr").build();
        User userToRemove = Customer.builder().username("brr").build();

        conversation.getUsers().add(user);
        conversation.getUsers().add(userToRemove);

        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);
        when(conversationRepository.save(conversation)).thenReturn(conversation);
        //ACT
        ConversationDTO conversationDTO = conversationService.removeUserFromConversationGroup(conversation.getId(),userToRemove.getUsername());

        //ASSERT
        assertNotNull(conversationDTO);
        assertEquals(1,conversationDTO.getUsernames().size());
    }

    @Test
    void sendMessageInConversation(){
        //ARRANGE
        Conversation conversation = Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build();
        SentMessage sentMessage = new SentMessage(new Message(1L,"sdaad","brr"));
        Message message = new Message(sentMessage,conversation);


        when(conversationRepository.getById(conversation.getId())).thenReturn(conversation);
        when(conversationRepository.save(conversation)).thenReturn(conversation);

        //ACT
        SentMessage newSentMessage = conversationService.sendMessageInConversation(conversation.getId(),sentMessage);

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
    void getConversation(){
        //ARRANGE
        Long id = 1L;
        when(conversationRepository.getById(id)).thenReturn(Conversation.builder()
                .id(1L)
                .creationDate(LocalDateTime.now())
                .name("GLowGanggg")
                .conversationType(ConversationType.GROUP).build());

        //ACT
        ConversationDTO conversationDTO = conversationService.getConversation(id);

        //ASSERT
        assertNotNull(conversationDTO);
    }

    @Test
    void getLastSentMessages(){
        //ARRANGE
        User user = Customer.builder().username("bigBrr").build();
        List<Conversation> conversations = Arrays.asList(
                Conversation.builder().id(1L).build()
        );
        conversations.get(0).getMessages().add(new Message(1L,"allo","brr"));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(conversationRepository.getByUser(user)).thenReturn(conversations);

        //ACT
        List<SentMessage> lastSentMessages = conversationService.getLastSentMessages(user.getUsername());

        //ASSERT
        assertEquals(1,lastSentMessages.size());
    }
}
