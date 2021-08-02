package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Profile;
import com.keita.musicbay.model.dto.ProfileToSubscribeTo;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    void getProfile(){
        //ARRANGE
        String username1 = "bigBrr";
        when(userRepository.findByUsername(username1)).thenReturn(Optional.of(Customer.builder().likings(Collections.emptyList()).sharings(Collections.emptyList()).purchasings(Collections.emptyList()).build()));

        //ACT
        Profile profileExist = userService.getProfile(username1);

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

        when(userRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        userService.getPicture(customer.getUsername(),mockHttpServletResponse);

    }

    @Test
    void getListProfileSearch(){
        //ARRANGE
        List<User> users = Arrays.asList(
                Customer.builder().username("brr").build(),
                Customer.builder().username("sdasd").build(),
                Customer.builder().username("sfettret").build(),
                Customer.builder().username("dfsdft").build()
        );
        when(userRepository.findAll()).thenReturn(users);

        //ACT
        List<ProfileToSubscribeTo> profilesSearched = userService.getListProfileSearch();

        //ASSERT
        assertEquals(4,profilesSearched.size());
    }

    @Test
    void setDefaultProfilePicture() throws Exception{

        //ACT
        byte[] pictureInBytes = userService.setDefaultProfilePicture();

        //ASSERT
        assertArrayEquals(new FileInputStream("./docs/noUser.jpg").readAllBytes(),pictureInBytes);

    }
}
