package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.enums.PriceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void checkIfTransactionPending() throws Exception{
        //ARRANGE
        String username = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/transaction/checkIfTransactionPending/" + username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void checkIfArticleIsInTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture1";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/transaction/checkIfArticleIsInTransaction/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username",username)
                .param("title",title)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("true",mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void createTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "redRoom";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/transaction/createTransaction/")
                .param("username",username)
                .param("title",title)
                .param("priceType", PriceType.BASIC.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void addArticleToTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture2";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/addArticleToTransaction/")
                .param("username",username)
                .param("title",title)
                .param("priceType",PriceType.EXCLUSIVE.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void removeArticleFromTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String title = "culture1";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/transaction/removeArticleFromTransaction/")
                .param("username",username)
                .param("title",title)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void cancelTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";
        String uuid = UUID.randomUUID().toString();

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/transaction/cancelTransaction/")
                .param("username",username)
                .param("uuid", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getCurrentTransaction() throws Exception{
        //ARRANGE
        String username = "bayDrip";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/transaction/getCurrentTransaction/" + username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

}
