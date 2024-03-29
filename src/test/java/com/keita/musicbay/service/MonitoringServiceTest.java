package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Log
public class MonitoringServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MusicRepository musicRepository;

    @Mock
    NotificationService notificationService;

    @Mock
    MusicService musicService;

    @InjectMocks
    MonitoringService monitoringService;


    @Test
    void likeMusic() {
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        doAnswer(invocationOnMock -> {
            Music musicUpdated = (Music) invocationOnMock.getArgument(0);
            musicUpdated.setNbrOfLike(musicUpdated.getNbrOfLike() + 1);
            return null;
        }).when(musicService).increaseLike(music);
        //ACT
        monitoringService.likeMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void unLikeMusic() {
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").likings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfLike(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        doAnswer(invocationOnMock -> {
            Music musicUpdated = invocationOnMock.getArgument(0);
            musicUpdated.setNbrOfLike(musicUpdated.getNbrOfLike() - 1);
            return null;
        }).when(musicService).decreaseLike(music);
        //ACT
        monitoringService.unLikeMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertNotNull(customer.getLikings().stream().filter(liking -> music.getTitle().equals(liking.getMusic().getTitle())));
    }

    @Test
    void shareMusic() {
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        doAnswer(invocationOnMock -> {
            Music musicUpdated = invocationOnMock.getArgument(0);
            musicUpdated.setNbrOfShare(musicUpdated.getNbrOfShare() + 1);
            return null;
        }).when(musicService).increaseShare(music);
        //ACT
        monitoringService.shareMusic(customer.getUsername(), music.getTitle(), "Nice one");

        //ASSERT
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void unShareMusic() {
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").sharings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfShare(4).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        doAnswer(invocationOnMock -> {
            Music musicUpdated = invocationOnMock.getArgument(0);
            musicUpdated.setNbrOfShare(musicUpdated.getNbrOfShare() - 1);
            return null;
        }).when(musicService).decreaseShare(music);
        //ACT
        monitoringService.unShareMusic(customer.getUsername(), music.getTitle());

        //ASSERT
        assertNotNull(customer.getSharings().stream().filter(sharing -> music.getTitle().equals(sharing.getMusic().getTitle())));
    }

    @Test
    void purchaseMusic() {
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").purchasings(new ArrayList<>()).build();
        Music music = Track.builder().title("IT").nbrOfPurchase(4).build();
        music.setProducer(Producer.builder().username("taa").build());

        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        doAnswer(invocationOnMock -> {
            Music musicUpdated = invocationOnMock.getArgument(0);
            musicUpdated.setNbrOfPurchase(musicUpdated.getNbrOfPurchase() + 1);
            return null;
        }).when(musicService).increasePurchase(music);

        //ACT
        monitoringService.purchaseMusic(customer, music.getTitle(), LocalDateTime.now());

        //ASSERT
        assertNotNull(customer.getPurchasings().stream().filter(purchasing -> music.getTitle().equals(purchasing.getMusic().getTitle())));
    }

    @Test
    void subscribe() {
        //ARRANGE
        Customer customer1 = Customer.builder().username("bigBrr").build();
        Customer customerToFollow1 = Customer.builder().username("c4").build();
        when(userRepository.findByUsername(customer1.getUsername())).thenReturn(Optional.of(customer1));
        when(userRepository.findByUsername(customerToFollow1.getUsername())).thenReturn(Optional.of(customerToFollow1));

        Customer customer2 = Customer.builder().username("araa").build();
        customer2.getSubscribeTos().add(SubscribeTo.builder().username("taa").build());
        when(userRepository.findByUsername(customer2.getUsername())).thenReturn(Optional.of(customer2));

        //ACT
        monitoringService.subscribe(customer1.getUsername(), customerToFollow1.getUsername());
        monitoringService.subscribe(customer2.getUsername(), "taa");

        //ASSERT
        assertEquals(1, customer1.getSubscribeTos().size());
        assertEquals(1, customerToFollow1.getSubscribers().size());
        assertEquals(1, customer2.getSubscribeTos().size());
    }

    @Test
    void unSubscribe() {
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        Customer customerToUnFollow = Customer.builder().username("c4").build();

        customer.getSubscribeTos().add(new SubscribeTo(customerToUnFollow.getUsername(), customer));

        when(userRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(userRepository.findByUsername(customerToUnFollow.getUsername())).thenReturn(Optional.of(customerToUnFollow));

        //ACT
        monitoringService.unSubscribe(customer.getUsername(), customerToUnFollow.getUsername());

        //ASSERT
        assertEquals(0, customer.getSubscribeTos().size());
    }

    @Test
    void checkIfSubscribeTo() {
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        SubscribeTo customerSubscribeTo = SubscribeTo.builder().username("c4").build();
        customer.getSubscribeTos().add(customerSubscribeTo);

        when(userRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        boolean customerIsSubscribedTo = monitoringService.checkIfSubscribeTo(customer.getUsername(), customerSubscribeTo.getUsername());

        //ASSERT
        assertTrue(customerIsSubscribedTo);
    }
}
