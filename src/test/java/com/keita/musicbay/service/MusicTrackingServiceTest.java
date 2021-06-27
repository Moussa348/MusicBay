package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.Track;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MusicTrackingServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    MusicTrackingService musicTrackingService;

    @BeforeEach
    void setup(){
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        when(musicRepository.saveAndFlush(any(Music.class))).thenReturn(new Track());
    }

    @Test
    void likeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        //ACT
        musicTrackingService.likeMusic(customer.getUserName(), music.getTitle());
    }

    @Test
    void shareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        //ACT
        musicTrackingService.shareMusic(customer.getUserName(), music.getTitle(),"Nice one");
    }

    @Test
    void purchaseMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").purchasings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfPurchase(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        //ACT
        musicTrackingService.purchaseMusic(customer.getUserName(), music.getTitle(), LocalDateTime.now());
    }
}
