package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void insert(){
        Customer customer = Customer.builder().username("bigWolf").build();
        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().customer(customer).build(),
                Transaction.builder().customer(customer).build(),
                Transaction.builder().customer(customer).build()
        );

        transactions.get(0).setConfirmed(true);
        transactions.get(1).setConfirmed(true);

        customer.setTransactions(transactions);

        customerRepository.save(customer);
    }

    @Test
    void findByCustomerUsernameAndConfirmedFalse(){
        //ARRANGE
        String username = "bigWolf";

        //ACT
        boolean currentTransactionPresent = transactionRepository.findByCustomerUsernameAndConfirmedFalse(username).isPresent();

        //ASSERT
        assertTrue(currentTransactionPresent);

    }
}
