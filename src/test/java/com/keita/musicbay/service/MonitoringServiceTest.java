package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitoringServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    MonitoringService monitoringService;


    @Test
    void likeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.likeMusic(customer.getUserName(), music.getTitle());

        //ASSERT
        assertEquals(5,music.getNbrOfLike());
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void unLikeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.unLikeMusic(customer.getUserName(), music.getTitle());

        //ASSERT
        assertEquals(3,music.getNbrOfLike());
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void shareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.shareMusic(customer.getUserName(), music.getTitle(),"Nice one");

        //ASSERT
        assertEquals(5,music.getNbrOfShare());
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void unShareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.unShareMusic(customer.getUserName(), music.getTitle());

        //ASSERT
        assertEquals(3,music.getNbrOfShare());
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void purchaseMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").purchasings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfPurchase(4).build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.purchaseMusic(customer.getUserName(), music.getTitle(), LocalDateTime.now());

        //ASSERT
        assertEquals(5,music.getNbrOfPurchase());
        assertNotNull(customer.getPurchasings().stream().filter(purchasing -> music.getTitle().equals(purchasing.getMusic().getTitle())));
    }

    @Test
    void subscribe(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").build();
        Customer customerToFollow = Customer.builder().userName("c4").build();
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(customerRepository.findByUserName(customerToFollow.getUserName())).thenReturn(Optional.of(customerToFollow));

        //ACT
        monitoringService.subscribe(customer.getUserName(),customerToFollow.getUserName());

        //ASSERT
        assertEquals(1,customer.getSubscribeTos().size());
        assertEquals(1,customerToFollow.getSubscribers().size());
    }

    @Test
    void unSubscribe(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").build();
        Customer customerToUnFollow = Customer.builder().userName("c4").build();

        customer.getSubscribeTos().add(new SubscribeTo(customerToUnFollow.getUserName(),customer));

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(customerRepository.findByUserName(customerToUnFollow.getUserName())).thenReturn(Optional.of(customerToUnFollow));

        //ACT
        monitoringService.unSubscribe(customer.getUserName(),customerToUnFollow.getUserName());

        //ASSERT
        assertEquals(0,customer.getSubscribeTos().size());
    }

    @Test
    void checkIfSubscribeTo(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").build();
        SubscribeTo customerSubscribeTo = SubscribeTo.builder().username("c4").build();
        customer.getSubscribeTos().add(customerSubscribeTo);

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        boolean customerIsSubscribedTo = monitoringService.checkIfSubscribeTo(customer.getUserName(),customerSubscribeTo.getUsername());

        //ASSERT
        assertTrue(customerIsSubscribedTo);
    }
}
