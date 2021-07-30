package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Transaction;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.TransactionRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private ArticleService articleService;

    public boolean checkIfTransactionPending(String username) {

        return transactionRepository.findByCustomerUsernameAndConfirmedFalse(username).isPresent();
    }

    public boolean checkIfArticleIsInTransaction(String username, String title) {
        Transaction currentTransaction = getTransactionByCustomerUsername(username);

        return currentTransaction.getArticles().stream().anyMatch(article -> article.getMusic().getTitle().equals(title));
    }


    @Transactional
    public TransactionDTO createTransaction(String username, String title, PriceType priceType) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer with : " + username));
        Transaction transaction = articleService.addArticleInTransaction(Transaction.builder().total(0f).customer(customer).build(), title, priceType);

        customer.getTransactions().add(transaction);

        customerRepository.save(customer);

        return new TransactionDTO(transaction);
    }

    public TransactionDTO addArticleInTransaction(String username, String title, PriceType priceType) {

        return new TransactionDTO(transactionRepository.save(articleService.addArticleInTransaction(getTransactionByCustomerUsername(username), title, priceType)));

    }

    public TransactionDTO removeArticleFromTransaction(String username, String title) {

        return new TransactionDTO(transactionRepository.save(articleService.removeArticleFromTransaction(getTransactionByCustomerUsername(username), title)));
    }

    public void cancelTransaction(String username, UUID uuid) throws Exception {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer with : " + username));
        List<Transaction> transactions = customer.getTransactions();

        if (transactions.stream().anyMatch(transaction -> transaction.getUuid().equals(uuid))) {
            customer.getTransactions().removeIf(transaction -> transaction.getUuid().equals(uuid));
            customerRepository.save(customer);
            emailService.sendCancellationEmail(customer, uuid);

        }
    }

    /*
       / TODO : call stripe
           -if no good --> emailService.sendCancellation()
           -if good ---> send confirmationEmail() + update


        */
    @Transactional
    public void confirmTransaction(String username) throws Exception {
        Transaction transaction = getTransactionByCustomerUsername(username);
        TransactionDTO transactionDTO = new TransactionDTO(transaction);
        List<String> musicArticleTitles = transactionDTO.getMusicArticles().stream().map(MusicArticle::getTitle).collect(Collectors.toList());

        musicArticleTitles.forEach(musicArticleTitle -> monitoringService.purchaseMusic(transaction.getCustomer(), musicArticleTitle, LocalDateTime.now()));

        log.info("CONFIRMING TRANSACTION");

        transaction.setConfirmed(true);

        transactionRepository.save(transaction);

        emailService.sendConfirmationEmail(transaction.getCustomer(), transactionDTO);
    }

    public TransactionDTO getCurrentTransaction(String username) {
        Transaction transaction = getTransactionByCustomerUsername(username);

        return transaction.isConfirmed() ? null : new TransactionDTO(transaction);
    }

    private Transaction getTransactionByCustomerUsername(String username) {
        return transactionRepository.findByCustomerUsernameAndConfirmedFalse(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No latest transaction for : " + username));
    }

}