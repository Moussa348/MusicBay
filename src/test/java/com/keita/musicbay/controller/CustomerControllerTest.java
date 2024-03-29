package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.dto.Registration;
import com.keita.musicbay.model.entity.Producer;
import com.keita.musicbay.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtProvider jwtProvider;

    String token;

    @BeforeEach
    void generateToken (){
        token = jwtProvider.generate(Producer.builder().username("bombay").roles("PRODUCER").build());
    }

    @Test
    void createCustomer() throws Exception{
        //ARRANGE
        Registration registration1 = Registration.builder().username("brr").email("brr@gmail.com").build();
        Registration registration2 = Registration.builder().username("bayDrip").email("bayDrip@gmail.com").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/customer/createCustomer")
                .content(mapper.writeValueAsString(registration1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/customer/createCustomer")
                .content(mapper.writeValueAsString(registration2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
        assertEquals("false",mvcResult2.getResponse().getContentAsString());
    }

    @Test
    void updateCustomer() throws Exception{
        //ARRANGE
        Registration registration1 = Registration.builder().username("brr").email("bigBrr@gmail.com").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/customer/updateCustomer")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .content(mapper.writeValueAsString(registration1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getProfile() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getProfile/" + username1)
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotEquals("",mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getPicture() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getPicture/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }
}
