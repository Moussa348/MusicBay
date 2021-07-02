package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
        Customer customer2 = Customer.builder().userName("bayDrip").build();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.multipart("/customer/createCustomer").file("picture","".getBytes())
                .content(mapper.writeValueAsString(customer1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.multipart("/customer/createCustomer").file("picture","".getBytes())
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
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getProfile/" + username1)
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
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getProfile/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListLikedMusic() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getListLikedMusic/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListSharedMusic() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getListSharedMusic/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListPurchasedMusic() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getListPurchasedMusic/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListSubscriber() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getListSubscriber/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListSubscribeTo() throws Exception{
        //ARRANGE
        String username1 = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/customer/getListSubscribeTo/" + username1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }
}
