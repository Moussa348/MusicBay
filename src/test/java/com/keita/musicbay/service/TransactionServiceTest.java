package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
    void createTransaction(){
        //ARRANGE
        Customer customer = Customer.builder().userName("brr").transactions(new ArrayList<>()).build();
        Transaction transaction = Transaction.builder().customer(customer).build();
        Music music = MixTape.builder().title("hoodSeason").build();

        customer.getTransactions().add(transaction);

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        when(customerRepository.save(customer)).thenReturn(customer);
        //ACT
        TransactionDTO createdTransaction = transactionService.createTransaction(customer.getUserName(),music.getTitle());

        //ASSERT
        assertNotNull(createdTransaction);
        assertEquals(1,createdTransaction.getMusicArticles().size());
    }

    @Test
    void addMusicToTransaction(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").transactions(Arrays.asList(Transaction.builder().build())).build();
        Music music = MixTape.builder().title("hoodSeason").build();

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));
        when(customerRepository.save(customer)).thenReturn(customer);

        //ACT
        TransactionDTO addedMusicTransaction = transactionService.addMusicToTransaction(customer.getUserName(),music.getTitle());

        //ASSERT
        assertEquals(1,addedMusicTransaction.getMusicArticles().size());
    }

    @Test
    void removeMusicFromTransaction(){
        //ARRANGE
        Transaction transaction = Transaction.builder().build();
        Music music = Track.builder().title("culture").build();
        Customer customer = Customer.builder().userName("bigBrr").transactions(Arrays.asList(transaction)).build();

        transaction.getMusics().add(music);

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        //ACT
        TransactionDTO removedMusicTransaction = transactionService.removeMusicFromTransaction(customer.getUserName(),music.getTitle());

        //ASSERT
        assertEquals(0,removedMusicTransaction.getMusicArticles().size());
    }

    @Test
    void cancelTransaction(){
        //ARRANGE
        Customer customer = Customer.builder().userName("bigBrr").transactions(new ArrayList<>()).build();

        customer.getTransactions().add(new Transaction());
        customer.getTransactions().add(new Transaction());
        customer.getTransactions().add(new Transaction());
        customer.getTransactions().get(0).setConfirmed(true);
        customer.getTransactions().get(1).setConfirmed(true);

        when(customerRepository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));

        //ACT
        transactionService.cancelTransaction(customer.getUserName());

        //ASSERT
        assertEquals(2,customer.getTransactions().size());
    }

    @Test
    void getCurrentTransaction(){
        //ARRANGE
        Customer customer1 =  Customer.builder().userName("bigBrr").transactions(Arrays.asList(Transaction.builder().build())).build();
        when(customerRepository.findByUserName(customer1.getUserName())).thenReturn(Optional.of(customer1));

        Customer customer2 =  Customer.builder().userName("50cent").transactions(new ArrayList<>()).build();
        when(customerRepository.findByUserName(customer2.getUserName())).thenReturn(Optional.of(customer2));

        //ACT
        TransactionDTO currentTransactionOfCustomer1 = transactionService.getCurrentTransaction(customer1.getUserName());
        TransactionDTO currentTransactionOfCustomer2 = transactionService.getCurrentTransaction(customer2.getUserName());

        //ASSERT
        assertNotNull(currentTransactionOfCustomer1);
        assertNull(currentTransactionOfCustomer2);
    }
}
