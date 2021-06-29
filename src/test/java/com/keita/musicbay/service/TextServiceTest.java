package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.TextDTO;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.TextRepository;
import com.keita.musicbay.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Log
public class TextServiceTest {

    @Mock
    TextRepository textRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    TextService textService;

    @Test
    void createMessage() {
        //ARRANGE
        TextDTO textDTO = new TextDTO("hey my friend","bigbrr", Arrays.asList("brr", "c4", "moneyman"));
        textDTO.getUsernames().forEach(username -> when(userRepository.findByUserName(username)).thenReturn(Optional.of(new Customer())));

        when(textRepository.save(any(Text.class))).thenReturn(Message.builder().content(textDTO.getContent()).build());

        //ACT
        String returnedContent = textService.createMessage(textDTO);

        //ASSERT
        assertEquals(textDTO.getContent(),returnedContent);
    }

    @Test
    public void deleteMessage(){
        //ARRANGE
        Long id = 1L;
        String username = "bombay";
        Message message = Message.builder().users(Arrays.asList(Customer.builder().userName("taa").build(),Customer.builder().userName("bombay").build())).build();
        when(textRepository.findById(id)).thenReturn(Optional.of(message));

        //ACT
        textService.deleteMessage(id,username);

        //ASSERT
        assertEquals(1,message.getUsers().size());

    }

    @Test
    void createComment() {
        //ARRANGE
        TextDTO textDTO = new TextDTO("this shit is fire no cap!!!","migos","culture3",0);
        Music music = Track.builder().title("culture3").build();
        when(musicRepository.findByTitle(textDTO.getMusicTitle())).thenReturn(Optional.of(music));
        when(textRepository.save(any(Text.class))).thenReturn(Comment.builder().content(textDTO.getContent()).music(music).build());

        //ACT
        String returnedContent = textService.createComment(textDTO).getContent();

        //ASSERT
        assertEquals(textDTO.getContent(),returnedContent);
    }

    @Test
    void increaseLike() {
        //ARRANGE
        Comment comment = Comment.builder().id(1L).nbrLike(5).music(Track.builder().title("halloween").build()).build();
        when(textRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        when(textRepository.save(comment)).thenReturn(comment);

        //ACT
        Integer nbrLike = textService.increaseLike(comment.getId()).getNbrLike();

        //ASSERT
        assertEquals(6,nbrLike);
    }
}
