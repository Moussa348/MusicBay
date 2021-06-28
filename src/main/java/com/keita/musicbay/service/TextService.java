package com.keita.musicbay.service;

import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.TextDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.TextRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TextService {

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private UserRepository userRepository;

    public String createMessage(TextDTO message){
        List<User> users = message
                .getUsernames()
                .stream()
                .map(username -> userRepository.findByUserName(username).get())
                .collect(Collectors.toList());
        return textRepository.save(new Message(message,users)).getContent();

    }
}
