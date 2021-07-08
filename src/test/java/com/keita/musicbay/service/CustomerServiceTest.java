package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;

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
        Registration registration1 = Registration.builder().userName("ice").email("ice@gmail.com").password("sadadas").build();
        when(customerRepository.existsByEmail(registration1.getEmail()) && customerRepository.existsByUserName(registration1.getUserName())).thenReturn(false);

        Registration registration2 = Registration.builder().userName("brrr").email("brrr@gmail.com").password("brrr").build();
        when(customerRepository.existsByEmail(registration2.getEmail()) && customerRepository.existsByUserName(registration2.getUserName())).thenReturn(true);

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
        Registration registration1 = Registration.builder().uuid(UUID.randomUUID()).userName("ice").email("ice@gmail.com").password("sadadas").build();
        Customer customer1 = Customer.builder().uuid(registration1.getUuid()).userName("ice").email("ice@gmail.com").build();

        Registration registration2 = Registration.builder().uuid(UUID.randomUUID()).userName("taa").email("bigBrr@gmail.com").password("sadadas").build();
        Customer customer2 = Customer.builder().uuid(registration2.getUuid()).userName("bigBrr").email("bigBrr@gmail.com").build();

        //ACT
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer(registration1,customer1)).thenReturn(new Customer(registration2,customer2));
        when(customerRepository.findById(registration1.getUuid())).thenReturn(Optional.of(customer1));
        Profile updateProfileWithSameUsername = customerService.updateCustomer(registration1);

        when(customerRepository.findById(registration2.getUuid())).thenReturn(Optional.of(customer2));
        when(customerRepository.existsByUserName(registration2.getUserName())).thenReturn(false);
        Profile updatedProfileWithModifiedUser = customerService.updateCustomer(registration2);

        //ASSERT
        assertEquals(registration1.getUserName(),updateProfileWithSameUsername.getUsername());
        assertEquals(registration2.getUserName(),updatedProfileWithModifiedUser.getUsername());
    }

    @Test
    void getProfile(){
        //ARRANGE
        String username1 = "bigBrr";
        when(customerRepository.findByUserName(username1)).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build()));

        //ACT
        Profile profileExist = customerService.getProfile(username1);

        //ASSERT
        assertNotNull(profileExist);
    }

    @Test
    void getPicture() throws IOException {
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").build();
        customer.setPicture("sadasd".getBytes());

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletResponse.setContentType("image/jpeg");

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        customerService.getPicture(customer.getUserName(),mockHttpServletResponse);

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
    void getListSubscriber(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").build();
        Subscriber subscriber = Subscriber.builder().username("taa").build();

        customer.setSubscribers(Arrays.asList(subscriber));

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        customer.getSubscribers().forEach(s -> when(customerRepository.findByUserName(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));

        //ACT
        List<Profile> subscribers = customerService.getListSubscriber(customer.getUserName());

        //ASSERT
        assertEquals(1,subscribers.size());
    }

    @Test
    void getListSubscribeTo(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bombay").build();
        SubscribeTo subscriberTo = SubscribeTo.builder().username("taa").build();

        customer.setSubscribeTos(Arrays.asList(subscriberTo));

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        customer.getSubscribeTos().forEach(s -> when(customerRepository.findByUserName(s.getUsername())).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build())));

        //ACT
        List<Profile> subscriberTos = customerService.getListSubscribeTo(customer.getUserName());

        //ASSERT
        assertEquals(1,subscriberTos.size());
    }
}
