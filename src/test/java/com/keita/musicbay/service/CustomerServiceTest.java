package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Log
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void createCustomer() throws Exception {
        //ARRANGE
        Registration registration1 = Registration.builder().username("ice").email("ice@gmail.com").password("sadadas").build();
        when(customerRepository.existsByEmail(registration1.getEmail()) && customerRepository.existsByUsername(registration1.getUsername())).thenReturn(false);

        Registration registration2 = Registration.builder().username("brrr").email("brrr@gmail.com").password("brrr").build();
        when(customerRepository.existsByEmail(registration2.getEmail()) && customerRepository.existsByUsername(registration2.getUsername())).thenReturn(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        boolean customer1HasBeenSaved = customerService.createCustomer(registration1);
        boolean customer2HasNotBeenSaved = customerService.createCustomer(registration2);

        //ASSERT
        assertTrue(customer1HasBeenSaved);
        assertFalse(customer2HasNotBeenSaved);
    }

    @Test
    void updateCustomer() throws Exception{
        //ARRANGE
        Registration registration1 = Registration.builder().uuid(UUID.randomUUID()).username("ice").email("ice@gmail.com").password("sadadas").build();
        Customer customer1 = Customer.builder().uuid(registration1.getUuid()).username("ice").email("ice@gmail.com").build();

        Registration registration2 = Registration.builder().uuid(UUID.randomUUID()).username("taa").email("bigBrr@gmail.com").password("sadadas").build();
        Customer customer2 = Customer.builder().uuid(registration2.getUuid()).username("bigBrr").email("bigBrr@gmail.com").build();

        //ACT
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer(registration1,customer1)).thenReturn(new Customer(registration2,customer2));
        when(customerRepository.findById(registration1.getUuid())).thenReturn(Optional.of(customer1));
        Profile updateProfileWithSameUsername = customerService.updateCustomer(registration1);

        when(customerRepository.findById(registration2.getUuid())).thenReturn(Optional.of(customer2));
        when(customerRepository.existsByUsername(registration2.getUsername())).thenReturn(false);
        Profile updatedProfileWithModifiedUser = customerService.updateCustomer(registration2);

        //ASSERT
        assertEquals(registration1.getUsername(),updateProfileWithSameUsername.getUsername());
        assertEquals(registration2.getUsername(),updatedProfileWithModifiedUser.getUsername());
    }

    @Test
    void getProfile(){
        //ARRANGE
        String username1 = "bigBrr";
        when(customerRepository.findByUsername(username1)).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build()));

        //ACT
        Profile profileExist = customerService.getProfile(username1);

        //ASSERT
        assertNotNull(profileExist);
    }

    @Test
    void getPicture() throws IOException {
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").build();
        customer.setPicture("sadasd".getBytes());

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletResponse.setContentType("image/jpeg");

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        customerService.getPicture(customer.getUsername(),mockHttpServletResponse);

    }

    @Test
    void getListLikedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").likings(Arrays.asList(Liking.builder().music(Track.builder().build()).build(), Liking.builder().music(Track.builder().build()).build())).build();
        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        List<LikedMusic> likedMusics = customerService.getListLikedMusic(customer.getUsername());

        //ASSERT
        assertEquals(2,likedMusics.size());
    }

    @Test
    void getListSharedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").sharings(Arrays.asList(Sharing.builder().music(Track.builder().build()).build(), Sharing.builder().music(Track.builder().build()).build())).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        List<SharedMusic> sharedMusics = customerService.getListSharedMusic(customer.getUsername());

        //ASSERT
        assertEquals(2,sharedMusics.size());
    }

    @Test
    void getListPurchasedMusic(){
        //ARRANGE
        Customer customer = Customer.builder().username("ceo").purchasings(Arrays.asList(Purchasing.builder().music(Track.builder().build()).build(), Purchasing.builder().music(Track.builder().build()).build())).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        List<PurchasedMusic> purchasedMusics = customerService.getListPurchasedMusic(customer.getUsername());

        //ASSERT
        assertEquals(2,purchasedMusics.size());
    }

    @Test
    void getListSubscriber(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").build();
        Subscriber subscriber = Subscriber.builder().username("taa").build();

        customer.setSubscribers(Arrays.asList(subscriber));

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        customer.getSubscribers().forEach(s -> when(customerRepository.findByUsername(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));

        //ACT
        List<Profile> subscribers = customerService.getListSubscriber(customer.getUsername());

        //ASSERT
        assertEquals(1,subscribers.size());
    }

    @Test
    void getListSubscribeTo(){
        //ARRANGE
        Customer customer = Customer.builder().username("bombay").build();
        SubscribeTo subscriberTo = SubscribeTo.builder().username("taa").build();

        customer.setSubscribeTos(Arrays.asList(subscriberTo));

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        customer.getSubscribeTos().forEach(s -> when(customerRepository.findByUsername(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));

        //ACT
        List<Profile> subscriberTos = customerService.getListSubscribeTo(customer.getUsername());

        //ASSERT
        assertEquals(1,subscriberTos.size());
    }
}
