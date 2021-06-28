package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.Text;
import com.keita.musicbay.model.dto.TextDTO;
import com.keita.musicbay.repository.TextRepository;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextServiceTest {

    @Mock
    TextRepository textRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TextService textService;

    @Test
    void createMessage() {
        //ARRANGE
        TextDTO textDTO = new TextDTO("hey my friend", Arrays.asList("brr", "c4", "moneyman"));
        textDTO.getUsernames().forEach(username -> when(userRepository.findByUserName(username)).thenReturn(Optional.of(new Customer())));

        when(textRepository.save(any(Text.class))).thenReturn(Message.builder().content(textDTO.getContent()).build());

        //ACT
        String returnedContent = textService.createMessage(textDTO);

        //ASSERT
        assertEquals(textDTO.getContent(),returnedContent);

    }
}
