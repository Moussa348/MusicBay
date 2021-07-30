package com.keita.musicbay.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.entity.Producer;
import com.keita.musicbay.security.JwtProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtProvider jwtProvider;

    String token;

    @BeforeAll
    void generateToken() {
        token = jwtProvider.generate(Producer.builder().username("bombay").roles("PRODUCER").build());
    }

    @Test
    void notificationSeen() throws Exception {
        //ARRANGE
        long id = 1L;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/notification/notificationSeen/" + id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();


        //ASSERT
        assertTrue(mvcResult1.getResolvedException() instanceof ResponseStatusException);
    }

    @Test
    void getRecentNotifications() throws Exception {
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/notification/getRecentNotifications/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .param("username", username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getNbrOfPage() throws Exception {
        //ARRANGE
        String username = "bombay";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/notification/getNbrOfPage/" + username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("1", mvcResult1.getResponse().getContentAsString());
    }
}
