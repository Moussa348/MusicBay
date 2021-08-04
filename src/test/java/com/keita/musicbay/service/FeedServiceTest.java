package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    LikingRepository likingRepository;

    @Mock
    SharingRepository sharingRepository;

    @Mock
    PurchasingRepository purchasingRepository;

    @Mock
    SubscriberRepository subscriberRepository;

    @Mock
    SubscribeToRepository subscribeToRepository;

    @InjectMocks
    FeedService feedService;


    @Test
    void getFeed(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").build();
        Customer customerSubscribeTo = Customer.builder().username("bigBrr").build();
        int noPage = 0;

        List<Liking> likings = Arrays.asList(
                Liking.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Liking.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );
        List<Sharing> sharings = Arrays.asList(
                Sharing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Sharing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );
        List<Purchasing> purchasings = Arrays.asList(
                Purchasing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );

        customer.getSubscribeTos().add(SubscribeTo.builder().username(customerSubscribeTo.getUsername()).build());

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(likingRepository.getAllByCustomerUsername(customerSubscribeTo.getUsername(),PageRequest.of(noPage,3,Sort.by("likingDate").descending()))).thenReturn(likings);
        when(sharingRepository.getAllByCustomerUsername(customerSubscribeTo.getUsername(),PageRequest.of(noPage,3,Sort.by("sharingDate").descending()))).thenReturn(sharings);
        when(purchasingRepository.getAllByCustomerUsername(customerSubscribeTo.getUsername(),PageRequest.of(noPage,3,Sort.by("purchasingDate").descending()))).thenReturn(purchasings);

        //ACT
        Feed yourFeed = feedService.getFeed(customer.getUsername(),0);

        //ASSERT
        assertNotNull(yourFeed);
        assertEquals(2,yourFeed.getLikedMusics().size());
        assertEquals(2,yourFeed.getSharedMusics().size());
        assertEquals(2,yourFeed.getPurchasedMusics().size());
    }


    @Test
    void getListPossibleSubscribeTo(){
        //ARRANGE
        User user = Customer.builder().username("brr").build();
        int noPage = 0;
        List<User> users = new ArrayList<>();

        users.add(Customer.builder().username("grr").build());
        users.add(Customer.builder().username("araa").build());
        users.add(Customer.builder().username("gav").build());

        user.getSubscribeTos().add(SubscribeTo.builder().username("araa").build());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.getAllByUsernameNot(user.getUsername(),PageRequest.of(noPage,10, Sort.by("username"))))
                .thenReturn(users.stream().filter(user1 -> ! user1.getUsername().equals(user.getSubscribeTos().get(0).getUsername())).collect(Collectors.toList()));
        //ACT
        List<ProfileToSubscribeTo> possibleSubscribeTos = feedService.getListPossibleSubscribeTo(user.getUsername(),0);

        //ASSERT
        assertEquals(2,possibleSubscribeTos.size());
    }

    @Test
    void getListLikedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").likings(new ArrayList<>()).build();
        int noPage = 0;
        List<Liking> likings = Arrays.asList(
                Liking.builder().customer(customer).music(Track.builder().build()).build(),
                Liking.builder().customer(customer).music(Track.builder().build()).build()
        );

        when(likingRepository.getAllByCustomerUsername(customer.getUsername(),PageRequest.of(noPage,5,Sort.by("likingDate").descending()))).thenReturn(likings);
        //ACT
        List<LikedMusic> likedMusics = feedService.getListLikedMusic(customer.getUsername(),0);

        //ASSERT
        assertEquals(2,likedMusics.size());
    }

    @Test
    void getListSharedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").likings(new ArrayList<>()).build();
        int noPage = 0;
        List<Sharing> sharings = Arrays.asList(
                Sharing.builder().customer(customer).music(Track.builder().build()).build(),
                Sharing.builder().customer(customer).music(Track.builder().build()).build()
        );

        when(sharingRepository.getAllByCustomerUsername(customer.getUsername(),PageRequest.of(noPage,5,Sort.by("sharingDate").descending()))).thenReturn(sharings);

        //ACT
        List<SharedMusic> sharedMusics = feedService.getListSharedMusic(customer.getUsername(),0);

        //ASSERT
        assertEquals(2,sharedMusics.size());
    }

    @Test
    void getListPurchasedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").likings(new ArrayList<>()).build();
        int noPage = 0;
        List<Purchasing> purchasings = Arrays.asList(
                Purchasing.builder().customer(customer).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customer).music(Track.builder().build()).build()
        );

        when(purchasingRepository.getAllByCustomerUsername(customer.getUsername(),PageRequest.of(noPage,5,Sort.by("purchasingDate").descending()))).thenReturn(purchasings);

        //ACT
        List<PurchasedMusic> purchasedMusics = feedService.getListPurchasedMusic(customer.getUsername(),0);

        //ASSERT
        assertEquals(2,purchasedMusics.size());
    }

    @Test
    void getListSubscriber(){
        //ARRANGE
        User user = Customer.builder().username("bombay").build();
        Subscriber subscriber = Subscriber.builder().username("taa").user(user).build();
        int noPage = 0;

        user.setSubscribers(Arrays.asList(subscriber));

        user.getSubscribers().forEach(s -> when(userRepository.findByUsername(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));
        when(subscriberRepository.getAllByUserUsername(user.getUsername(),PageRequest.of(noPage,10, Sort.by("date").descending()))).thenReturn(user.getSubscribers());
        //ACT
        List<Profile> subscribers = feedService.getListSubscriber(user.getUsername(),noPage);

        //ASSERT
        assertEquals(1,subscribers.size());
    }

    @Test
    void getListSubscribeTo(){
        //ARRANGE
        User user = Customer.builder().username("bombay").build();
        SubscribeTo subscriberTo = SubscribeTo.builder().username("taa").build();
        int noPage = 0;

        user.setSubscribeTos(Arrays.asList(subscriberTo));

        user.getSubscribeTos().forEach(s -> when(userRepository.findByUsername(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));
        when(subscribeToRepository.getAllByUserUsername(user.getUsername(),PageRequest.of(noPage,10, Sort.by("date").descending()))).thenReturn(user.getSubscribeTos());

        //ACT
        List<Profile> subscriberTos = feedService.getListSubscribeTo(user.getUsername(),noPage);

        //ASSERT
        assertEquals(1,subscriberTos.size());
    }

    @Test
    void getNbrOfPageSub(){
        //ARRANGE
        String username = "hope";
        when(subscriberRepository.countAllByUserUsername(username)).thenReturn(15.2);

        //ACT
        Integer nbrOfPage = feedService.getNbrOfPageSub(username);

        //ASSERT
        assertEquals(2,nbrOfPage);
    }

    @Test
    void getNbrOfPageSubTo(){
        //ARRANGE
        String username = "hope";
        when(subscribeToRepository.countAllByUserUsername(username)).thenReturn(15.2);

        //ACT
        Integer nbrOfPage = feedService.getNbrOfPageSubTo(username);

        //ASSERT
        assertEquals(2,nbrOfPage);
    }

    @Test
    void getNbrOfPagePossibleSubTo(){
        //ARRANGE
        String username = "hope";
        when(userRepository.countAllByUsernameNot(username)).thenReturn(15.2);

        //ACT
        Integer nbrOfPage = feedService.getNbrOfPagePossibleSubTo(username);

        //ASSERT
        assertEquals(4,nbrOfPage);
    }
}
