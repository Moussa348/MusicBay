package com.keita.musicbay.service;

import com.keita.musicbay.model.Article;
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

    public boolean checkIfTransactionPending(String username){
        Customer customer = customerRepository.findByUserName(username).get();

        return !customer.getTransactions().isEmpty() && !customer.getTransactions().get(customer.getTransactions().size() - 1).isConfirmed();
    }


    @Transactional
    public TransactionDTO createTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Transaction transaction = Transaction.builder().customer(customer).build();

        transaction.getMusics().add(musicRepository.findByTitle(title).get());
        customer.getTransactions().add(transaction);

        customerRepository.save(customer);

        return new TransactionDTO(transaction);
    }

    @Transactional
    public TransactionDTO createTransaction(String username, Article article) {
        Customer customer = customerRepository.findByUserName(username).get();
        Transaction transaction = Transaction.builder().customer(customer).build();

        transaction.getArticles().add(musicRepository.findByTitle(title).get());
        customer.getTransactions().add(transaction);

        customerRepository.save(customer);

        return new TransactionDTO(transaction);
    }

    public TransactionDTO addMusicToTransaction(String username, String title) {
        Customer customer = customerRepository.findByUserName(username).get();
        Music music = musicRepository.findByTitle(title).get();
        Transaction latestTransaction = customer.getTransactions().get(customer.getTransactions().size()-1);

        latestTransaction.getMusics().add(music);

        customerRepository.save(customer);

        return new TransactionDTO(latestTransaction);
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

        return customer.getTransactions().get(positionOfLastTransaction).isConfirmed() ? null:new TransactionDTO(customer.getTransactions().get(positionOfLastTransaction));
    }
}