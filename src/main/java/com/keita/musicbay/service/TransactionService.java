package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MusicRepository musicRepository;


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

        Transaction transaction = customer.getTransactions().get(customer.getTransactions().size() - 1);

        transaction.getMusics().add(music);

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size() - 1));
    }

    public TransactionDTO removeMusicFromTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Transaction transaction = customer.getTransactions().get(customer.getTransactions().size() - 1);

        transaction.setMusics(transaction.getMusics().stream().filter(music -> !music.getTitle().equals(title)).collect(Collectors.toList()));

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size() - 1));
    }

    public void cancelTransaction(String username) {
        Customer customer = customerRepository.findByUserName(username).get();
        customer.getTransactions().remove(customer.getTransactions().size() - 1);
        customerRepository.save(customer);
    }

    public TransactionDTO getCurrentTransaction(String username) {
        Customer customer = customerRepository.findByUserName(username).get();
        if (!customer.getTransactions().isEmpty()) {

            Transaction transaction = customer.getTransactions().get(customer.getTransactions().size() - 1);

            if (transaction.getDate().toLocalDate().equals(LocalDate.now()))
                return new TransactionDTO(transaction);

        }
        return null;
    }
}