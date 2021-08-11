package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.MessageRepository;
import com.keita.musicbay.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public ConversationDTO createConversation(ConversationDTO conversationDTO){
        List<User> users = conversationDTO.getUsernames().stream().map(username -> userRepository.findByUsername(username).get()).collect(Collectors.toList());

        conversationRepository.save(new Conversation(conversationDTO,users));

        return conversationDTO;
    }

    public void addUserInConversationGroup(Long id, String username){
        Conversation conversation = conversationRepository.getById(id);

        conversation.getUsers().add(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with username: " + username)));
        conversationRepository.save(conversation);
    }

    public void removeUserFromConversationGroup(Long id,String username){
        Conversation conversation = conversationRepository.getById(id);

        conversation.getUsers().removeIf(user -> user.getUsername().equals(username));

        conversationRepository.save(conversation);
    }

    @Transactional
    public SentMessage sendMessageInConversation(SentMessage sentMessage){
        Conversation conversation = conversationRepository.getById(sentMessage.getConversationId());

        conversation.getMessages().add(new Message(sentMessage,conversation));

        conversationRepository.save(conversation);

        return sentMessage;
    }

    public void deleteConversation(Long id){
        Conversation conversation = conversationRepository.getById(id);
        conversation.setActive(false);

        conversationRepository.save(conversation);
    }

    public List<SentMessage> getMessagesFromConversation(Long id,Integer noPage){
        return messageRepository.getAllByConversationIdAndConversationActiveTrue(id, PageRequest.of(noPage,20, Sort.by("date").descending()))
                .stream().map(SentMessage::new).collect(Collectors.toList());
    }

    public List<SentMessage> getLastSentMessages(String username,Integer noPage){
        List<Message> lastSentMessages = new ArrayList<>();
        List<Conversation> conversations = conversationRepository.getByUserAndConversationTrue(userRepository.findByUsername(username).get(),PageRequest.of(noPage,20)).stream().filter(Conversation::isActive).collect(Collectors.toList());

        conversations.stream().filter(conversation -> !conversation.getMessages().isEmpty()).forEach(conversation -> lastSentMessages.add(conversation.getMessages().get(conversation.getMessages().size()-1)));

        return lastSentMessages.stream().map(SentMessage::new).collect(Collectors.toList());
    }
}
