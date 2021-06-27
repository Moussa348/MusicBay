package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    MusicService musicService;

    /*


    @Test
    void likeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().likedMusics(new ArrayList<>()).build();
        Liking liking = Liking.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(liking.getTitle())).thenReturn(Optional.of(liking));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.likeMusic(customer.getUserName(), liking.getTitle());

    }

    @Test
    void shareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().sharedMusics(new ArrayList<>()).build();
        Sharing sharing = Sharing.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(sharing.getTitle())).thenReturn(Optional.of(sharing));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.shareMusic(customer.getUserName(), sharing.getTitle());
    }

    @Test
    void purchaseMusic(){
        //ARRANGE
        Customer customer = Customer.builder().purchasedMusics(new ArrayList<>()).build();
        Purchasing purchasing = Purchasing.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(purchasing.getTitle())).thenReturn(Optional.of(purchasing));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.purchaseMusic(customer.getUserName(), purchasing.getTitle());
    }
     */

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
    void getListMusic(){
        //ARRANGE
        List<Music> musics = Arrays.asList(
                Track.builder().build(),
                MixTape.builder().build(),
                MixTape.builder().build(),
                Track.builder().build()
        );

        when(musicRepository.findAll()).thenReturn(musics);
        //ACT
        List<MusicDTO> musicDTOS = musicService.getListMusic();

        //ASSERT
        assertEquals(4,musicDTOS.size());

    }
}
