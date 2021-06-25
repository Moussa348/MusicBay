package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.Follower;
import com.keita.musicbay.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
    void saveCustomer(){
        //ARRANGE
        Customer customer1 = Customer.builder().userName("ice").email("ice@gmail.com").build();
        when(customerRepository.existsByEmail(customer1.getEmail()) && customerRepository.existsByUserName(customer1.getUserName())).thenReturn(false);

        Customer customer2 = Customer.builder().userName("brrr").email("brrr@gmail.com").build();
        when(customerRepository.existsByEmail(customer2.getEmail()) && customerRepository.existsByUserName(customer2.getUserName())).thenReturn(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        boolean customer1HasBeenSaved = customerService.saveCustomer(customer1);
        boolean customer2HasNotBeenSaved = customerService.saveCustomer(customer2);

        //ASSERT
        assertTrue(customer1HasBeenSaved);
        assertFalse(customer2HasNotBeenSaved);
    }

    @Test
    void follow(){
        //ARRANGE
        String username = "bigBrr";
        String usernameToFollow = "c4";
        when(customerRepository.findByUserName(username)).thenReturn(Optional.of(Customer.builder().build()));
        when(customerRepository.findByUserName(usernameToFollow)).thenReturn(Optional.of(Customer.builder().build()));

        //ACT
        Follower addedFollower = customerService.follow(username,usernameToFollow);

        //ASSERT
        assertNotNull(addedFollower);

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
