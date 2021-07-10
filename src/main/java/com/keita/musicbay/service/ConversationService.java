package com.keita.musicbay.service;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserRepository userRepository;

    public ConversationDTO createConversation(ConversationDTO conversationDTO){
        List<User> users = conversationDTO.getUsernames().stream().map(username -> userRepository.findByUsername(username).get()).collect(Collectors.toList());

        conversationRepository.save(new Conversation(conversationDTO,users));

        return conversationDTO;
    }

    public ConversationDTO addUserInConversationGroup(Long id, String username){
        Conversation conversation = conversationRepository.getById(id);

        conversation.getUsers().add(userRepository.findByUsername(username).get());

        return new ConversationDTO(conversationRepository.save(conversation));
    }

    public ConversationDTO removeUserFromConversationGroup(Long id,String username){
        Conversation conversation = conversationRepository.getById(id);

        conversation.getUsers().removeIf(user -> user.getUsername().equals(username));

        return new ConversationDTO(conversationRepository.save(conversation));
    }

    @Transactional
    public SentMessage sendMessageInConversation(Long conversationId,SentMessage sentMessage){
        Conversation conversation = conversationRepository.getById(conversationId);

        conversation.getMessages().add(new Message(sentMessage,conversation));

        return new SentMessage(conversationRepository.save(conversation).getMessages().get(conversation.getMessages().size()-1));
    }

    public void deleteConversation(Long id){
        Conversation conversation = conversationRepository.getById(id);
        conversation.setActive(false);

        conversationRepository.save(conversation);
    }

    public ConversationDTO getConversation(Long id){
        return new ConversationDTO(conversationRepository.getById(id));
    }

    public List<SentMessage> getLastSentMessages(String username){
        List<Message> lastSentMessages = new ArrayList<>();
        List<Conversation> conversations = conversationRepository.getByUser(userRepository.findByUsername(username).get()).stream().filter(Conversation::isActive).collect(Collectors.toList());

        conversations.forEach(conversation -> lastSentMessages.add(conversation.getMessages().get(conversation.getMessages().size()-1)));

        return lastSentMessages.stream().map(SentMessage::new).collect(Collectors.toList());
    }
}
