package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.MixTape;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.Transaction;
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

    @Mock
    TransactionRepository transactionRepository;

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
        TransactionDTO transactionDTO = transactionService.createTransaction(customer.getUserName(),music.getTitle());

        //ASSERT
        assertNotNull(transactionDTO);
        assertEquals(1,transactionDTO.getMusicArticles().size());
    }
}
