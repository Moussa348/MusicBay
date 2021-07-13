package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class FeedControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getFeed() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getFeed/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListPossibleSubscribeTo() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListPossibleSubscribeTo/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());

    }

    @Test
    void getListLikedMusic() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListLikedMusic/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());

    }

    @Test
    void getListSharedMusic() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListSharedMusic/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }


    @Test
    void getListPurchasedMusic() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListPurchasedMusic/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListSubscriber() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListSubscriber/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getListSubscribeTo() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/feed/getListSubscribeTo/")
                .param("username",username)
                .param("noPage", Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }
}
