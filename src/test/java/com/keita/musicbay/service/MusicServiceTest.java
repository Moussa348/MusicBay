package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Catalog;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    MusicRepository musicRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    MusicService musicService;

    @Test
    void getMusic(){
        //ARRANGE
        String title = "IceBurr";
        when(musicRepository.findByTitle(title)).thenReturn(Optional.of(Track.builder().build()));

        //ACT
        MusicDTO musicDTO = musicService.getMusic(title);

        //ASSERT
        assertNotNull(musicDTO);

    }

    @Test
    void getCatalog(){
        //ARRANGE
        Customer customer = Customer.builder()
                .sharings(Arrays.asList(Sharing.builder().music(Track.builder().title("").build()).build()))
                .likings(Arrays.asList(Liking.builder().music(Track.builder().title("").build()).build())).build();
        int noPage = 0;
        List<Music> musics = Arrays.asList(
                Track.builder().title("").build(),
                MixTape.builder().title("").build(),
                MixTape.builder().title("").build(),
                Track.builder().title("").build()
        );

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findAll(PageRequest.of(noPage, 30, Sort.by("date").descending()))).thenReturn(new PageImpl<>(musics));
        //ACT
        Catalog catalog = musicService.getCatalog(customer.getUsername(),noPage);

        //ASSERT
        assertEquals(4,catalog.getMusics().size());
        assertEquals(1,catalog.getLikedMusicTitles().size());
        assertEquals(1,catalog.getSharedMusicTitles().size());
    }

    @Test
    void getListMusic(){
        //ARRANGE
        List<Music> musics = Arrays.asList(
                Track.builder().build(),
                MixTape.builder().build(),
                MixTape.builder().build(),
                Track.builder().build()
        );
        int noPage = 0;

        when(musicRepository.findAll(PageRequest.of(noPage, 30, Sort.by("date").descending()))).thenReturn(new PageImpl<>(musics));
        //ACT
        List<MusicDTO> musicDTOS = musicService.getListMusic(noPage);

        //ASSERT
        assertEquals(4,musicDTOS.size());
    }

    @Test
    void increaseLike(){
        //ARRANGE
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        //ACT
        musicService.increaseLike(music);

        //ASSERT
        assertEquals(5,music.getNbrOfLike());

    }

    @Test
    void decreaseLike(){
        //ARRANGE
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        //ACT
        musicService.decreaseLike(music);

        //ASSERT
        assertEquals(3,music.getNbrOfLike());

    }

    @Test
    void increaseShare(){
        //ARRANGE
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        //ACT
        musicService.increaseShare(music);

        //ASSERT
        assertEquals(5,music.getNbrOfShare());
    }

    @Test
    void decreaseShare(){
        //ARRANGE
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        //ACT
        musicService.decreaseShare(music);

        //ASSERT
        assertEquals(3,music.getNbrOfShare());
    }

    @Test
    void increasePurchase(){
        //ARRANGE
        Music music = Track.builder().title("IT").nbrOfPurchase(4).build();

        //ACT
        musicService.increasePurchase(music);

        //ASSERT
        assertEquals(5,music.getNbrOfPurchase());
    }

}
