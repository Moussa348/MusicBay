package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    MusicRepository musicRepository;

    @Mock
    EmailService emailService;

    @Mock
    MonitoringService monitoringService;

    @Mock
    ArticleService articleService;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void checkIfTransactionPending(){
        //ARRANGE
        String username1 = "brrr";
        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(username1)).thenReturn(Optional.of(new Transaction()));

        String username2 = "grrr";
        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(username2)).thenReturn(Optional.empty());

        //ACT
        boolean oneTransactionPending = transactionService.checkIfTransactionPending(username1);
        boolean noTransactionPending = transactionService.checkIfTransactionPending(username2);

        //ASSERT
        assertTrue(oneTransactionPending);
        assertFalse(noTransactionPending);
    }

    @Test
    void checkIfArticleIsInTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().customer(Customer.builder().username("massou").build()).build();
        Article article = Article.builder().transaction(transaction).music(MixTape.builder().producer(Producer.builder().username("taa").build()).title("hoodSeason").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build();

        transaction.getArticles().add(article);

        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(transaction.getCustomer().getUsername())).thenReturn(Optional.of(transaction));

        //ACT
        boolean articleIsInTransaction = transactionService.checkIfArticleIsInTransaction(transaction.getCustomer().getUsername(),article.getMusic().getTitle());

        //ASSERT
        assertTrue(articleIsInTransaction);
    }

    @Test
    void createTransaction(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").transactions(new ArrayList<>()).build();
        Transaction transactionCreated = Transaction.builder().total(24.5f).customer(customer).build();
        Article article = Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(24.5f).producer(Producer.builder().username("taa").build()).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).transaction(transactionCreated).build();

        transactionCreated.getArticles().add(article);

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(articleService.addArticleInTransaction(any(Transaction.class),any(String.class),any(PriceType.class))).thenReturn(transactionCreated);
        when(customerRepository.save(customer)).thenReturn(customer);
        //ACT
       TransactionDTO returnedTransaction = transactionService.createTransaction(customer.getUsername(),article.getMusic().getTitle(),article.getPriceType());

        //ASSERT
        assertNotNull(returnedTransaction);
        assertEquals(1,returnedTransaction.getMusicArticles().size());
        assertEquals(24.5f,returnedTransaction.getMusicArticles().get(0).getPrice());
    }


    @Test
    void addArticleInTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().total(50.0f).customer(Customer.builder().username("bigBrr").build()).build();
        transaction.getArticles().add(Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(25.0f).exclusivePrice(30.0f).producer(Producer.builder().username("taa").build()).build()).transaction(transaction).priceType(PriceType.BASIC).build());

        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(transaction.getCustomer().getUsername())).thenReturn(Optional.of(transaction));

        transaction.getArticles().add( Article.builder().music(MixTape.builder().title("hoodSeason2").basicPrice(25.0f).exclusivePrice(30.0f).producer(Producer.builder().username("taa").build()).build()).transaction(transaction).priceType(PriceType.BASIC).build());

        when(articleService.addArticleInTransaction(any(Transaction.class),any(String.class),any(PriceType.class))).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        //ACT
        TransactionDTO addedMusicTransaction = transactionService.addArticleInTransaction(transaction.getCustomer().getUsername(),transaction.getArticles().get(1).getMusic().getTitle(),PriceType.BASIC);

        //ASSERT
        assertEquals(2,addedMusicTransaction.getMusicArticles().size());
        assertEquals(50.0f,addedMusicTransaction.getTotal());
    }


    @Test
    void removeArticleFromTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().total(25.0f).customer(Customer.builder().username("bigBrr").build()).build();
        Article article = Article.builder().transaction(transaction).priceType(PriceType.BASIC).music(Track.builder().producer(Producer.builder().username("taa").build()).title("culture").basicPrice(25.0f).build()).build();
        transaction.getArticles().add(article);

        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(transaction.getCustomer().getUsername())).thenReturn(Optional.of(transaction));

        transaction.getArticles().remove(0);
        transaction.setTotal(0.0f);

        when(articleService.removeArticleFromTransaction(any(Transaction.class),any(String.class))).thenReturn(transaction);

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        //ACT
        TransactionDTO removedMusicTransaction = transactionService.removeArticleFromTransaction(transaction.getCustomer().getUsername(),article.getMusic().getTitle());

        //ASSERT
        assertEquals(0,removedMusicTransaction.getMusicArticles().size());
        assertEquals(0,removedMusicTransaction.getTotal());
    }

    @Test
    void cancelTransaction() throws Exception{
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").transactions(new ArrayList<>()).build();

        customer.getTransactions().add(Transaction.builder().build());
        customer.getTransactions().add(Transaction.builder().build());
        customer.getTransactions().add(Transaction.builder().build());
        customer.getTransactions().get(0).setConfirmed(true);
        customer.getTransactions().get(1).setConfirmed(true);



        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        transactionService.cancelTransaction(customer.getUsername(),customer.getTransactions().get(1).getUuid());

        //ASSERT
        assertEquals(2,customer.getTransactions().size());
    }

    @Test
    void confirmTransaction(){
        //ARRANGE
        Customer customer1 =  Customer.builder().username("bigBrr").transactions(Arrays.asList(Transaction.builder().build())).build();

        //ACT

        //ASSERT
    }

    @Test
    void getCurrentTransaction(){
        //ARRANGE
        String username = "brrr";
        when(transactionRepository.findByCustomerUsernameAndConfirmedFalse(username)).thenReturn(Optional.of(Transaction.builder().total(24.5f).customer(Customer.builder().username(username).build()).build()));
        //ACT
        TransactionDTO currentTransactionOfCustomer1 = transactionService.getCurrentTransaction(username);

        //ASSERT
        assertNotNull(currentTransactionOfCustomer1);
    }
}
