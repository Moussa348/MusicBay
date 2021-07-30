package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.entity.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class MusicRepositoryTest {

    @Autowired
    MusicRepository musicRepository;

    @BeforeEach
    void insert(){
        List<Music> musics = Arrays.asList(
                Track.builder().title("hope").build(),
                Track.builder().title("hope2").build(),
                Track.builder().title("hope3").build(),
                Track.builder().title("hope4").build(),
                Track.builder().title("hope5").build(),
                Track.builder().title("hope6").build(),
                Track.builder().title("hope7").build(),
                Track.builder().title("hope8").build()
        );
        musicRepository.saveAll(musics);
    }

    @Test
    void existsByTitle(){
        //ARRANGE
        Music music1 = Track.builder().title("hope").build();
        Music music2 = Track.builder().title("h0p3").build();

        //ACT
        boolean musicExist = musicRepository.existsByTitle(music1.getTitle());
        boolean musicDoNotExist = musicRepository.existsByTitle(music2.getTitle());

        //ASSERT
        assertTrue(musicExist);
        assertFalse(musicDoNotExist);
    }

    @Test
    void findByTitle(){
        //ARRANGE
        Music music1 = Track.builder().title("hope").build();
        Music music2 = Track.builder().title("h0p3").build();

        //ACT
        boolean musicExist = musicRepository.findByTitle(music1.getTitle()).isPresent();
        boolean musicDoNotExist = musicRepository.findByTitle(music2.getTitle()).isPresent();

        //ASSERT
        assertTrue(musicExist);
        assertFalse(musicDoNotExist);
    }

    @Test
    void getAllBy(){
        //ARRANGE
        PageRequest pageRequest = PageRequest.of(0,3);

        //ACT
        Integer nbrOfPages = musicRepository.getAllBy(pageRequest).getTotalPages();

        //ASSERT
        assertEquals(3,nbrOfPages);
    }

    @Test
    void countAllBy(){
        //ACT
        Double nbrOfMusic = musicRepository.countAllBy();

        //ASSERT
        assertEquals(8,nbrOfMusic);
    }
}
