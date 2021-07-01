package com.keita.musicbay.service;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;


    public ConversationDTO createConversation(ConversationDTO conversationDTO){
        List<User> users = conversationDTO.getUsernames().stream().map(username -> userRepository.findByUserName(username).get()).collect(Collectors.toList());

        Conversation conversation = Conversation.builder()
                .creationDate(conversationDTO.getCreationDate())
                .name(conversationDTO.getName())
                .conversationType(conversationDTO.getConversationType()).build();

        conversation.getUser().addAll(users);

        return  new ConversationDTO(conversationRepository.save(conversation));
    }

    public ConversationDTO addUserInConversationGroup(Long id, String username){
        Conversation conversation = conversationRepository.getById(id);

        conversation.getUser().add(userRepository.findByUserName(username).get());

        return new ConversationDTO(conversationRepository.save(conversation));
    }


}
