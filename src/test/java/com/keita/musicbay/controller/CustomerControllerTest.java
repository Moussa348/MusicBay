package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void createCustomer() throws Exception{
        //ARRANGE
        Customer customer1 = Customer.builder().userName("brr").build();
        Customer customer2 = Customer.builder().userName("francois123").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/customer/createCustomer")
                .content(mapper.writeValueAsString(customer1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/customer/createCustomer")
                .content(mapper.writeValueAsString(customer2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
        assertEquals("false",mvcResult2.getResponse().getContentAsString());
    }

    @Test
    void getProfile() throws Exception{
        //ARRANGE
        String username1 = "brr";
        String username2 = "francois123";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/getProfile/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/getProfile/" + username2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("",mvcResult1.getResponse().getContentAsString());
        assertNotEquals("",mvcResult2.getResponse().getContentAsString());
    }
}
