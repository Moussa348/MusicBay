package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.TransactionRepository;
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
        assertEquals(2,addedMusicTransaction.getMusicArticles().size());
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
}
