package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Track;
import com.keita.musicbay.model.entity.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.enums.PriceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender  javaMailSender;

    @Mock
    FileService fileService;

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
    void sendCancellationEmail() throws Exception{
        //ARRANGE
        Customer customer = Customer.builder().email("araa@gmail.com").build();

        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        //ACT
        emailService.sendCancellationEmail(customer, UUID.randomUUID());
    }

    @Test
    void sendMusicArticles() throws Exception{
        //ARRANGE
        Customer customer = Customer.builder().email("araaaa@gmail.com").build();
        List<MusicArticle> musicArticles = Arrays.asList(
                new MusicArticle(Article.builder().music(Track.builder().title("adad").build()).priceType(PriceType.BASIC).build()),
                new MusicArticle(Article.builder().music(Track.builder().title("trretfds").build()).priceType(PriceType.BASIC).build()),
                new MusicArticle(Article.builder().music(Track.builder().title("vcxffr").build()).priceType(PriceType.BASIC).build())
        );

        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        musicArticles.forEach(musicArticle -> {
            try {
                when(fileService.getFile(musicArticle.getTitle() + ".mp3")).thenReturn(new File(""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //ACT
        emailService.sendMusicArticles(customer,musicArticles);
    }
}
