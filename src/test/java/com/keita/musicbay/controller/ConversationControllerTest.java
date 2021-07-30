package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.model.entity.Producer;
import com.keita.musicbay.model.enums.ConversationType;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConversationControllerTest {

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
    void createConversation() throws Exception{
        //ARRANGE
        ConversationDTO conversationDTO = ConversationDTO.builder().createdBy("brr").name("glowGang").conversationType(ConversationType.GROUP).usernames(Arrays.asList("bigBrr","bombay")).build();
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/conversation/createConversation/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .content(mapper.writeValueAsString(conversationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void addUserInConversationGroup() throws Exception{
        //ARRANGE
        Long id = 1L;
        String username = "bayDrip";
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/conversation/addUserInConversationGroup/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("id",String.valueOf(id))
                .param("username",username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void removeUserFromConversationGroup() throws Exception{
        //ARRANGE
        Long id = 2L;
        String username = "bayDrip";
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/conversation/removeUserFromConversationGroup/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("id",String.valueOf(id))
                .param("username",username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void sendMessageInConversation() throws Exception{
        //ARRANGE
        SentMessage sentMessage = new SentMessage(Message.builder().conversation(Conversation.builder().id(1L).build()).content("allo").sendBy("brrr").build());
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/conversation/sendMessageInConversation/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .content(mapper.writeValueAsString(sentMessage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void deleteConversation() throws Exception{
        //ARRANGE
        long conversationId = 2L;
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.delete("/conversation/deleteConversation/" + conversationId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getMessagesFromConversation() throws Exception{
        //ARRANGE
        long id = 2L;
        int noPage = 0;
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/conversation/getMessagesFromConversation/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("id",String.valueOf(id))
                .param("noPage",String.valueOf(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getLastSentMessages() throws Exception{
        //ARRANGE
        String username = "bombay";
        int noPage = 0;
        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/conversation/getLastSentMessages/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("username",username)
                .param("noPage",String.valueOf(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }
}
