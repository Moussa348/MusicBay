package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @BeforeEach
    void insert() {

        List<Transaction> transactions = Arrays.asList(
                Transaction.builder().build()
        );

        Customer customer = Customer.builder().userName("brr").transactions(transactions).build();
        customerRepository.save(customer);

    }

    @Test
    void findByCustomerUserNameAndDate() {
        //ARRANGE
        String username = "brr";
        LocalDateTime dateTime = LocalDateTime.now();

        //ACT

        //ASSERT
    }
}
