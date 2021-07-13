package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.enums.PriceType;
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
        Customer customer = customerRepository.findByUsername(username).get();

        return !customer.getTransactions().isEmpty() && !customer.getTransactions().get(customer.getTransactions().size() - 1).isConfirmed();
    }

    public boolean checkIfArticleIsInTransaction(String username,String title){
        Customer customer = customerRepository.findByUsername(username).get();
        Transaction latestTransaction = customer.getTransactions().get(customer.getTransactions().size()-1);

        return latestTransaction.getArticles().stream().anyMatch(article -> article.getMusic().getTitle().equals(title));
    }


    @Transactional
    public TransactionDTO createTransaction(String username, String title, PriceType priceType) {
        Customer customer = customerRepository.findByUsername(username).get();
        Transaction transaction = Transaction.builder().customer(customer).build();
        Article article = new Article(priceType,transaction,musicRepository.findByTitle(title).get());

        transaction.getArticles().add(article);
        transaction.setTotal(article.getPrice());
        customer.getTransactions().add(transaction);

        customerRepository.save(customer);

        return new TransactionDTO(transaction);
    }

    public TransactionDTO addArticleToTransaction(String username, String title,PriceType priceType) {
        Customer customer = customerRepository.findByUsername(username).get();
        Transaction latestTransaction = customer.getTransactions().get(customer.getTransactions().size()-1);
        Article article = new Article(priceType,latestTransaction,musicRepository.findByTitle(title).get());

        latestTransaction.getArticles().add(article);
        latestTransaction.setTotal(latestTransaction.getTotal() + article.getPrice());

        customerRepository.save(customer);

        return new TransactionDTO(latestTransaction);
    }

    public TransactionDTO removeArticleFromTransaction(String username, String title) {
        Customer customer = customerRepository.findByUsername(username).get();
        Transaction transaction = customer.getTransactions().get(customer.getTransactions().size() - 1);

        transaction.getArticles().removeIf(article -> article.getMusic().getTitle().equals(title));

        return new TransactionDTO(customerRepository.save(customer).getTransactions().get(customer.getTransactions().size() - 1));
    }

    public void cancelTransaction(String username, UUID uuid) {
        Customer customer = customerRepository.findByUsername(username).get();

        customer.getTransactions().removeIf(transaction -> transaction.getUuid().equals(uuid));

        customerRepository.save(customer);
    }

    public TransactionDTO getCurrentTransaction(String username) {
        Customer customer = customerRepository.findByUsername(username).get();
        int positionOfLastTransaction = customer.getTransactions().size()-1;

        return customer.getTransactions().get(positionOfLastTransaction).isConfirmed() ? null:new TransactionDTO(customer.getTransactions().get(positionOfLastTransaction));
    }
}