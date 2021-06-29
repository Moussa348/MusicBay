package com.keita.musicbay.service;

import com.keita.musicbay.model.Comment;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.TextDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
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

    @Autowired
    private MusicRepository musicRepository;

    public String createMessage(TextDTO message){
        List<User> users = message
                .getUsernames()
                .stream()
                .map(username -> userRepository.findByUserName(username).get())
                .collect(Collectors.toList());
        return textRepository.save(new Message(message,users)).getContent();
    }

    public void deleteMessage(Long id,String username){
        Message message = (Message) textRepository.findById(id).get();
        List<User> users = message.getUsers().stream().filter(user -> !user.getUserName().equals(username)).collect(Collectors.toList());
        message.setUsers(users);
        textRepository.save(message);
    }

    public TextDTO createComment(TextDTO comment){
        Music music = musicRepository.findByTitle(comment.getMusicTitle()).get();
        return new TextDTO(textRepository.save(new Comment(comment,music)));
    }

    public TextDTO increaseLike(Long id){
        Comment comment = (Comment) textRepository.findById(id).get();
        comment.setNbrLike(comment.getNbrLike()+1);
        return new TextDTO(textRepository.save(comment));
    }

    public List<TextDTO> getListCommentOfMusic(String title){
        Music music = musicRepository.findByTitle(title).get();
        return music.getComments().stream().map(TextDTO::new).collect(Collectors.toList());
    }
}
