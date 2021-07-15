package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CommentRepositoryTest {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void insert(){
        List<Music> musics = Arrays.asList(
                Track.builder().title("hope").build()
        );

        musicRepository.saveAll(musics);

        List<Comment> comments = Arrays.asList(
                Comment.builder().music(musicRepository.findByTitle("hope").get()).build(),
                Comment.builder().music(musicRepository.findByTitle("hope").get()).build(),
                Comment.builder().music(musicRepository.findByTitle("hope").get()).build()
        );

        commentRepository.saveAll(comments);
    }

    @Test
    void getAllByMusicTitle(){
        //ARRANGE
        String title = "hope";

        //ACT
        List<Comment> comments = commentRepository.getAllByMusicTitle("hope", PageRequest.of(0,3, Sort.by("date").descending()));
        //ASSERT
        assertEquals(3,comments.size());
    }
}
