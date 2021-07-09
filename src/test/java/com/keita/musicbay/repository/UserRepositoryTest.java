package com.keita.musicbay.repository;


import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Liking;
import com.keita.musicbay.model.Track;
import com.keita.musicbay.model.User;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                Customer.builder().email("cancre@gmail.com").username("bigWolf22")
                        .likings(Arrays.asList(Liking.builder().music(Track.builder().title("ice").build()).build())).build()
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
}
