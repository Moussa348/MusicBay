package com.keita.musicbay.repository;


import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Liking;
import com.keita.musicbay.model.entity.Track;
import com.keita.musicbay.model.entity.User;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Log
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;



    @BeforeEach
    void insert(){
        List<User> users = Arrays.asList(
                Customer.builder().email("cancre@gmail.com").username("bigWolf22").build(),
                Customer.builder().email("cancre@gmail.com").username("bigBrr").build(),
                Customer.builder().email("cancre@gmail.com").username("brr").build(),
                Customer.builder().email("cancre@gmail.com").username("offset").build(),
                Customer.builder().email("cancre@gmail.com").username("fghdt").build()
        );
        userRepository.saveAll(users);
    }

    @Test
    void findByUserName(){
        //ARRANGE
        Customer customer1 = Customer.builder().username("sdaasdas").build();
        Customer customer2 = Customer.builder().username("bigWolf22").build();

        //ACT
        Customer customer = (Customer)  userRepository.findByUsername("bigWolf22").get();

        log.info(customer.toString());

        //ASSERT
    }

    @Test
    void getAllByUsernameNot(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<User> users = userRepository.getAllByUsernameNot(username, PageRequest.of(0,5,Sort.by("username")));

        //ASSERT
        assertEquals(4,users.size());
    }

    @Test
    void countAllByUsernameNot(){
        //ARRANGE
        String username = "brr";

        //ACT
        Double nbrOfUser = userRepository.countAllByUsernameNot(username);

        //ASSERT
        assertEquals(4,nbrOfUser);
    }
}
