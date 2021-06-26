package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    MusicRepository musicRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    MusicService musicService;

    @Test
    void likeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().likedMusics(new ArrayList<>()).build();
        LikedMusic likedMusic = LikedMusic.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(likedMusic.getTitle())).thenReturn(Optional.of(likedMusic));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.likeMusic(customer.getUserName(),likedMusic.getTitle());

    }

    @Test
    void shareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().sharedMusics(new ArrayList<>()).build();
        SharedMusic sharedMusic = SharedMusic.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(sharedMusic.getTitle())).thenReturn(Optional.of(sharedMusic));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.shareMusic(customer.getUserName(),sharedMusic.getTitle());
    }

    @Test
    void purchaseMusic(){
        //ARRANGE
        Customer customer = Customer.builder().purchasedMusics(new ArrayList<>()).build();
        PurchasedMusic purchasedMusic = PurchasedMusic.builder().title("IT").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(purchasedMusic.getTitle())).thenReturn(Optional.of(purchasedMusic));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        musicService.purchaseMusic(customer.getUserName(),purchasedMusic.getTitle());
    }
}
