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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class MonitoringControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void likeMusic() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture1";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/monitoring/likeMusic/")
                .param("username",username)
                .param("title",title)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void unLikeMusic() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture2";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/monitoring/unLikeMusic/")
                .param("username",username)
                .param("title",title)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void shareMusic() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture1";
        String sharingMsg = "Yall need to listen to this this firee";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/monitoring/shareMusic/")
                .param("username",username)
                .param("title",title)
                .param("sharingMsg",sharingMsg)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void unShareMusic() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture2";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/monitoring/unShareMusic/")
                .param("username",username)
                .param("title",title)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void subscribe() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String usernameToFollow = "bigBrr";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/monitoring/subscribe/")
                .param("username",username)
                .param("usernameToFollow",usernameToFollow)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void unSubscribe() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String usernameToUnFollow = "bigBrr";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/monitoring/unSubscribe/")
                .param("username",username)
                .param("usernameToUnFollow",usernameToUnFollow)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void checkIfSubscribeTo() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String usernameSubscribeTo = "bombay";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/monitoring/checkIfSubscribeTo/")
                .param("username",username)
                .param("usernameSubscribeTo",usernameSubscribeTo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
    }
}
