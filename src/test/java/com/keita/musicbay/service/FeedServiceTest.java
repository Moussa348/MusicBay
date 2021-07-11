package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    void getFeed(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").build();
        List<Liking> likings = Arrays.asList(
                Liking.builder().customer(customer).music(Track.builder().build()).build(),
                Liking.builder().customer(customer).music(Track.builder().build()).build(),
                Liking.builder().customer(customer).music(Track.builder().build()).build()
        );
        List<Sharing> sharings = Arrays.asList(
                Sharing.builder().customer(customer).music(Track.builder().build()).build(),
                Sharing.builder().customer(customer).music(Track.builder().build()).build(),
                Sharing.builder().customer(customer).music(Track.builder().build()).build()
        );
        List<Purchasing> purchasings = Arrays.asList(
                Purchasing.builder().customer(customer).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customer).music(Track.builder().build()).build(),
                Purchasing.builder().customer(customer).music(Track.builder().build()).build()
        );

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(likingRepository.getByCustomerAndLikingDateBetween(customer, LocalDateTime.now().minusDays(5),LocalDateTime.now())).thenReturn(likings);
        when(sharingRepository.getByCustomerAndSharingDateBetween(customer, LocalDateTime.now().minusDays(5),LocalDateTime.now())).thenReturn(sharings);
        when(purchasingRepository.getByCustomerAndPurchasingDateBetween(customer, LocalDateTime.now().minusDays(5),LocalDateTime.now())).thenReturn(purchasings);


        //ACT
        Feed yourFeed = feedService.getFeed(customer.getUsername(),LocalDateTime.now());

        //ASSERT
        assertEquals(3,yourFeed.getLikedMusics().size());
        assertEquals(3,yourFeed.getSharedMusics().size());
        assertEquals(3,yourFeed.getPurchasedMusics().size());
        assertTrue(yourFeed.getProfiles().isEmpty());
    }
}
