package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MusicRepository musicRepository;

    public TransactionDTO createTransaction(String username,String title){
        Customer customer = customerRepository.findByUserName(username).get();

        customer.setTransactions(new ArrayList<>());

        customer.getTransactions().add(Transaction.builder()
                .customer(customer)
                .musics(Arrays.asList(musicRepository.findByTitle(title).get())).build());
        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size()-1));
    }
}
