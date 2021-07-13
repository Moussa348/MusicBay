package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @InjectMocks
    FeedService feedService;

    @Test
    void getFeed() {
        //ARRANGE
        Customer customer = Customer.builder().username("brr").build();
        Customer customerSubscribeTo = Customer.builder().username("bigBrr").build();
        LocalDateTime date = LocalDateTime.parse("2021-07-12 07:27", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        int nbrOfDays = 2;

        List<Liking> likings = Arrays.asList(
                Liking.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Liking.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Liking.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );
        List<Sharing> sharings = Arrays.asList(
                Sharing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Sharing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Sharing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );
        List<Purchasing> purchasings = Arrays.asList(
                Purchasing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customerSubscribeTo).music(Track.builder().build()).build()
        );

        customer.getSubscribeTos().add(SubscribeTo.builder().username(customerSubscribeTo.getUsername()).build());
        customerSubscribeTo.setLikings(likings);
        customerSubscribeTo.setSharings(sharings);
        customerSubscribeTo.setPurchasings(purchasings);

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customerSubscribeTo)).thenReturn(Optional.of(customerSubscribeTo));
        customer.getSubscribeTos().forEach(subscribeTo -> when(customerRepository.findByUsername(subscribeTo.getUsername())).thenReturn(Optional.of(customerSubscribeTo)));

        when(likingRepository.getByCustomerAndLikingDateBetween(customerSubscribeTo, date.minusDays(nbrOfDays), date)).thenReturn(likings);
        when(sharingRepository.getByCustomerAndSharingDateBetween(customerSubscribeTo, date.minusDays(nbrOfDays), date)).thenReturn(sharings);
        when(purchasingRepository.getByCustomerAndPurchasingDateBetween(customerSubscribeTo, date.minusDays(nbrOfDays), date)).thenReturn(purchasings);

        //ACT
        Feed yourFeed = feedService.getFeed(customerSubscribeTo.getUsername(), "2021-07-12 07:27", nbrOfDays);

        //ASSERT
        //assertEquals(3, yourFeed.getLikedMusics().size());
        assertEquals(3, yourFeed.getSharedMusics().size());
        assertEquals(3, yourFeed.getPurchasedMusics().size());
        assertTrue(yourFeed.getProfiles().isEmpty());
    }
}
