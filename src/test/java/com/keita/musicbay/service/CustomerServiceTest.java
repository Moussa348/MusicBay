package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void createCustomer(){
        //ARRANGE
        Customer customer1 = Customer.builder().userName("ice").email("ice@gmail.com").build();
        when(customerRepository.existsByEmail(customer1.getEmail()) && customerRepository.existsByUserName(customer1.getUserName())).thenReturn(false);

        Customer customer2 = Customer.builder().userName("brrr").email("brrr@gmail.com").build();
        when(customerRepository.existsByEmail(customer2.getEmail()) && customerRepository.existsByUserName(customer2.getUserName())).thenReturn(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        boolean customer1HasBeenSaved = customerService.createCustomer(customer1);
        boolean customer2HasNotBeenSaved = customerService.createCustomer(customer2);

        //ASSERT
        assertTrue(customer1HasBeenSaved);
        assertFalse(customer2HasNotBeenSaved);
    }

    @Test
    void getProfile(){
        //ARRANGE
        String username1 = "bigBrr";
        when(customerRepository.findByUserName(username1))
                .thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build()));

        //ACT
        Profile profileExist = customerService.getProfile(username1);

        //ASSERT
        assertNotNull(profileExist);
    }

    @Test
    void follow(){
        //ARRANGE
        String username = "bigBrr";
        String usernameToFollow = "c4";
        when(customerRepository.findByUserName(username)).thenReturn(Optional.of(Customer.builder().build()));
        when(customerRepository.findByUserName(usernameToFollow)).thenReturn(Optional.of(Customer.builder().build()));

        //ACT
        customerService.follow(username,usernameToFollow);
    }

    @Test
    void getListLikedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("ceo").likings(Arrays.asList(Liking.builder().music(Track.builder().build()).build(), Liking.builder().music(Track.builder().build()).build())).build();
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        List<LikedMusic> likedMusics = customerService.getListLikedMusic(customer.getUserName());

        //ASSERT
        assertEquals(2,likedMusics.size());
    }

    @Test
    void getListSharedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("ceo").sharings(Arrays.asList(Sharing.builder().music(Track.builder().build()).build(), Sharing.builder().music(Track.builder().build()).build())).build();
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        List<SharedMusic> sharedMusics = customerService.getListSharedMusic(customer.getUserName());

        //ASSERT
        assertEquals(2,sharedMusics.size());
    }

    @Test
    void getListPurchasedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().userName("ceo").purchasings(Arrays.asList(Purchasing.builder().music(Track.builder().build()).build(), Purchasing.builder().music(Track.builder().build()).build())).build();
        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        List<PurchasedMusic> purchasedMusics = customerService.getListPurchasedMusic(customer.getUserName());

        //ASSERT
        assertEquals(2,purchasedMusics.size());
    }

    @Test
    void getListFollower(){
        //ARRANGE
        Customer customer1 = Customer.builder().userName("ice").email("ice@gmail.com").build();
        customer1.setUsers(Arrays.asList(Customer.builder().build(),Customer.builder().build()));
        when(customerRepository.findByUserName(customer1.getUserName())).thenReturn(Optional.of(customer1));

        Customer customer2 = Customer.builder().userName("brrr").email("brrr@gmail.com").build();
        when(customerRepository.findByUserName(customer2.getUserName())).thenReturn(Optional.of(customer2));

        //ACT
        List<Follower> followersOfCustomer1 = customerService.getListFollower(customer1.getUserName());
        List<Follower> followersOfCustomer2 = customerService.getListFollower(customer2.getUserName());

        //ASSERT
        assertEquals(2,followersOfCustomer1.size());
        assertEquals(0,followersOfCustomer2.size());
    }
}
