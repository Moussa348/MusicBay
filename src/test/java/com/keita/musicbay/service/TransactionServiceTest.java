package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void checkIfTransactionPending(){
        //ARRANGE
        Customer customer1 = Customer.builder().username("brr").transactions(new ArrayList<>()).build();
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().build(),
                Transaction.builder().build()
        );
        transactions.get(0).setConfirmed(true);
        transactions.get(1).setConfirmed(false);

        customer1.setTransactions(transactions);
        when(customerRepository.findByUsername(customer1.getUsername())).thenReturn(Optional.of(customer1));
        when(customerRepository.findByUsername(customer1.getUsername())).thenReturn(Optional.of(customer1));

        Customer customer2 = Customer.builder().username("bay").transactions(new ArrayList<>()).build();
        when(customerRepository.findByUsername(customer2.getUsername())).thenReturn(Optional.of(customer2));


        //ACT
        boolean oneTransactionPending = transactionService.checkIfTransactionPending(customer1.getUsername());
        boolean noTransactionPending = transactionService.checkIfTransactionPending(customer2.getUsername());
        //ASSERT
        assertTrue(oneTransactionPending);
        assertFalse(noTransactionPending);
    }

    @Test
    void checkIfArticleIsInTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().build();
        Article article = Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build();
        transaction.getArticles().add(article);

        Customer customer = Customer.builder().username("bigBrr").transactions(new ArrayList<>()).build();
        customer.getTransactions().add(transaction);
        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        //ACT
        boolean articleIsInTransaction = transactionService.checkIfArticleIsInTransaction(customer.getUsername(),article.getMusic().getTitle());

        //ASSERT
        assertTrue(articleIsInTransaction);
    }

    @Test
    void createTransaction(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").transactions(new ArrayList<>()).build();
        Article article = Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build();


        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(article.getMusic().getTitle())).thenReturn(Optional.of(article.getMusic()));
        when(customerRepository.save(customer)).thenReturn(customer);
        //ACT
       TransactionDTO createdTransaction = transactionService.createTransaction(customer.getUsername(),article.getMusic().getTitle(),article.getPriceType());

        //ASSERT
        assertNotNull(createdTransaction);
        assertEquals(1,createdTransaction.getMusicArticles().size());
        assertEquals(24.5f,createdTransaction.getMusicArticles().get(0).getPrice());
    }


    @Test
    void addArticleToTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().total(24.5f).build();
        transaction.getArticles().add(Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build());

        Customer customer = Customer.builder().username("bigBrr").transactions(Arrays.asList(transaction)).build();
        Article article = Article.builder().music(MixTape.builder().title("hoodSeason2").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build();

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(article.getMusic().getTitle())).thenReturn(Optional.of(article.getMusic()));
        when(customerRepository.save(customer)).thenReturn(customer);
        //ACT
        TransactionDTO addedMusicTransaction = transactionService.addArticleToTransaction(customer.getUsername(),article.getMusic().getTitle(),PriceType.BASIC);

        //ASSERT
        assertEquals(2,addedMusicTransaction.getMusicArticles().size());
        assertEquals(49.0f,addedMusicTransaction.getTotal());
    }


    @Test
    void removeArticleFromTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().build();
        Article article = Article.builder().priceType(PriceType.BASIC).music(Track.builder().title("culture").build()).build();
        Customer customer = Customer.builder().username("bigBrr").transactions(Arrays.asList(transaction)).build();

        transaction.getArticles().add(article);

        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        //ACT
        TransactionDTO removedMusicTransaction = transactionService.removeArticleFromTransaction(customer.getUsername(),article.getMusic().getTitle());

        //ASSERT
        assertEquals(0,removedMusicTransaction.getMusicArticles().size());
    }

    @Test
    void cancelTransaction(){
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
    void getCurrentTransaction(){
        //ARRANGE
        Customer customer1 =  Customer.builder().username("bigBrr").transactions(Arrays.asList(Transaction.builder().build())).build();
        when(customerRepository.findByUsername(customer1.getUsername())).thenReturn(Optional.of(customer1));

        //ACT
        TransactionDTO currentTransactionOfCustomer1 = transactionService.getCurrentTransaction(customer1.getUsername());

        //ASSERT
        assertNotNull(currentTransactionOfCustomer1);
    }
}
