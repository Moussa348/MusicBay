package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.entity.SubscribeTo;
import com.keita.musicbay.model.entity.Track;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Customer customer = Customer.builder().username("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.likeMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertEquals(5,music.getNbrOfLike());
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void unLikeMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.unLikeMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertEquals(3,music.getNbrOfLike());
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void shareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.shareMusic(customer.getUsername(), music.getTitle(),"Nice one");

        //ASSERT
        assertEquals(5,music.getNbrOfShare());
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void unShareMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.unShareMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertEquals(3,music.getNbrOfShare());
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void purchaseMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").purchasings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfPurchase(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        monitoringService.purchaseMusic(customer.getUsername(), music.getTitle(), LocalDateTime.now());

        //ASSERT
        assertEquals(5,music.getNbrOfPurchase());
        assertNotNull(customer.getPurchasings().stream().filter(purchasing -> music.getTitle().equals(purchasing.getMusic().getTitle())));
    }

    @Test
    void subscribe(){
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        Customer customerToFollow = Customer.builder().username("c4").build();
        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(customerRepository.findByUsername(customerToFollow.getUsername())).thenReturn(Optional.of(customerToFollow));

        //ACT
        monitoringService.subscribe(customer.getUsername(),customerToFollow.getUsername());

        //ASSERT
        assertEquals(1,customer.getSubscribeTos().size());
        assertEquals(1,customerToFollow.getSubscribers().size());
    }

    @Test
    void unSubscribe(){
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        Customer customerToUnFollow = Customer.builder().username("c4").build();

        customer.getSubscribeTos().add(new SubscribeTo(customerToUnFollow.getUsername(),customer));

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(customerRepository.findByUsername(customerToUnFollow.getUsername())).thenReturn(Optional.of(customerToUnFollow));

        //ACT
        monitoringService.unSubscribe(customer.getUsername(),customerToUnFollow.getUsername());

        //ASSERT
        assertEquals(0,customer.getSubscribeTos().size());
    }

    @Test
    void checkIfSubscribeTo(){
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        SubscribeTo customerSubscribeTo = SubscribeTo.builder().username("c4").build();
        customer.getSubscribeTos().add(customerSubscribeTo);

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        boolean customerIsSubscribedTo = monitoringService.checkIfSubscribeTo(customer.getUsername(),customerSubscribeTo.getUsername());

        //ASSERT
        assertTrue(customerIsSubscribedTo);
    }
}
