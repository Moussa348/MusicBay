package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.PostedComment;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Log
public class CommentServiceTest {

    @Mock
    TextRepository textRepository;

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void postComment() {
        //ARRANGE
        PostedComment postedComment = new PostedComment("this shit is fire no cap!!!","migos",0);
        Music music = Track.builder().title("culture3").build();
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        when(textRepository.save(any(Text.class))).thenReturn(Comment.builder().content(postedComment.getContent()).music(music).build());

        //ACT
        String returnedContent = commentService.postComment(postedComment,music.getTitle()).getContent();

        //ASSERT
        assertEquals(postedComment.getContent(),returnedContent);
    }

    @Test
    void increaseLike() {
        //ARRANGE
        Comment comment = Comment.builder().id(1L).nbrLike(5).music(Track.builder().title("halloween").build()).build();
        String username = "bombay";

        when(textRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(textRepository.save(comment)).thenReturn(comment);

        //ACT
        Integer nbrLike = commentService.increaseLike(comment.getId(),username).getNbrLike();

        //ASSERT
        assertEquals(6,nbrLike);
    }

    @Test
    void decreaseLike(){
        //ARRANGE
        Comment comment = Comment.builder().id(1L).nbrLike(5).music(Track.builder().title("halloween").build()).build();
        String username = "bombay";

        when(textRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(textRepository.save(comment)).thenReturn(comment);

        //ACT
        Integer nbrLike = commentService.decreaseLike(comment.getId(),username).getNbrLike();

        //ASSERT
        assertEquals(4,nbrLike);
    }

    @Test
    void getListCommentOfMusic(){
        //ARRANGE
        Music music = Track.builder().title("culture3").build();
        music.setComments(Arrays.asList(new Comment(),new Comment()));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        List<PostedComment> postedComments = commentService.getListCommentOfMusic(music.getTitle());

        //ASSERT
        assertEquals(2,postedComments.size());
    }
}
