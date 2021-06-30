package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender  javaMailSender;

    @InjectMocks
    EmailService emailService;

    @Test
    void sendConfirmationEmail() throws Exception{
        //ARRANGE
        Customer customer = Customer.builder().email("araa@gmail.com").build();
        TransactionDTO transactionDTO = new TransactionDTO(Transaction.builder().build());

        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        //ACT
        emailService.sendConfirmationEmail(customer,transactionDTO);
    }

    @Test
    void sendCancellation() throws Exception{
        //ARRANGE
        Customer customer = Customer.builder().email("araa@gmail.com").build();
        TransactionDTO transactionDTO = new TransactionDTO(Transaction.builder().build());

        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        //ACT
        emailService.sendCancellation(customer,transactionDTO);
    }
}
