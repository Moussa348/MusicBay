package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.model.entity.Comment;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.entity.Track;
import com.keita.musicbay.repository.CommentRepository;
import com.keita.musicbay.repository.MusicRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    CommentRepository commentRepository;

    @Mock
    MusicRepository musicRepository;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    CommentService commentService;

    @Test
    void postComment() {
        //ARRANGE
        PostedComment postedComment = new PostedComment("this shit is fire no cap!!!","migos",0);
        Music music = Track.builder().title("culture3").build();
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        when(commentRepository.save(any())).thenReturn(Comment.builder().content(postedComment.getContent()).music(music).build());

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

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any())).thenReturn(comment);

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

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        //ACT
        Integer nbrLike = commentService.decreaseLike(comment.getId(),username).getNbrLike();

        //ASSERT
        assertEquals(4,nbrLike);
    }

    @Test
    void getListCommentOfMusic(){
        //ARRANGE
        Music music = Track.builder().title("culture3").build();
        int noPage = 0;
        List<Comment> comments = Arrays.asList(Comment.builder().music(music).build(),Comment.builder().music(music).build());

        when(commentRepository.getAllByMusicTitle(music.getTitle(), PageRequest.of(noPage,10, Sort.by("date").descending()))).thenReturn(comments);
        //ACT
        List<PostedComment> postedComments = commentService.getListCommentOfMusic(music.getTitle(),noPage);

        //ASSERT
        assertEquals(2,postedComments.size());
    }

    @Test
    void getNbrOfPage(){
        //ARRANGE
        String title = "hope";
        when(commentRepository.countAllByMusicTitle(title)).thenReturn(4.2);
        //ACT
        Integer nbrOfPage = commentService.getNbrOfPage(title);

        //ASSERT
        assertEquals(1,nbrOfPage);
    }
}
