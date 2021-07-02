package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Transactional
    public TransactionDTO createTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Transaction transaction = Transaction.builder().customer(customer).build();

        transaction.getMusics().add(musicRepository.findByTitle(title).get());
        customer.getTransactions().add(transaction);

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size() - 1));
    }

    public TransactionDTO addMusicToTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Music music = musicRepository.findByTitle(title).get();
        int positionOfLastTransaction = customer.getTransactions().size()-1;

        customer.getTransactions().get(positionOfLastTransaction).getMusics().add(music);

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(positionOfLastTransaction));
    }

    public TransactionDTO removeMusicFromTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Transaction transaction = customer.getTransactions().get(customer.getTransactions().size() - 1);

        transaction.getMusics().removeIf(music -> music.getTitle().equals(title));

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size() - 1));
    }

    public void cancelTransaction(String username, UUID uuid) {
        Customer customer = customerRepository.findByUserName(username).get();

        customer.getTransactions().removeIf(transaction -> transaction.getUuid().equals(uuid));

        customerRepository.save(customer);
    }

    public TransactionDTO getCurrentTransaction(String username) {
        Customer customer = customerRepository.findByUserName(username).get();
        int positionOfLastTransaction = customer.getTransactions().size()-1;

        return customer.getTransactions().isEmpty() || customer.getTransactions().get(positionOfLastTransaction).isConfirmed() ? null:new TransactionDTO(customer.getTransactions().get(positionOfLastTransaction));
    }
}