package com.keita.musicbay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.musicbay.model.dto.PostedComment;
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
public class CommentControllerTest {

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
    void postComment() throws Exception{
        //ARRANGE
        PostedComment postedComment = new PostedComment("wooow","arra",0);
        String title = "redRoom";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/comment/postComment/" + title)
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .content(mapper.writeValueAsString(postedComment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void increaseLike() throws Exception{
        //ARRANGE
        long id = 1L;
        String username = "bombay";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/comment/increaseLike/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("id",String.valueOf(id))
                .param("username",username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();


        //ASSERT
        assertTrue(mvcResult1.getResolvedException() instanceof ResponseStatusException);
    }

    @Test
    void decreaseLike() throws Exception{
        //ARRANGE
        long id = 1L;
        String username = "bombay";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.patch("/comment/decreaseLike/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("id",String.valueOf(id))
                .param("username",username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();


        //ASSERT
        assertTrue(mvcResult1.getResolvedException() instanceof ResponseStatusException);
    }

    @Test
    void getListCommentOfMusic() throws Exception{
        //ARRANGE
        String title = "redRoom";
        int noPage = 0;

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/comment/getListCommentOfMusic/")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .param("title",title)
                .param("noPage",Integer.toString(noPage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //ASSERT
        assertNotNull(mvcResult1.getResponse().getContentAsString());
    }

    @Test
    void getNbrOfPage() throws Exception{
        //ARRANGE
        String title = "culture1";

        //ACT
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/comment/getNbrOfPage/" + title )
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //ASSERT
        assertEquals("1",mvcResult1.getResponse().getContentAsString());
    }


}
