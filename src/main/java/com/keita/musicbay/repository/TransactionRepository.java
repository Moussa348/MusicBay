package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByCustomerUsernameAndDate(String username,LocalDateTime dateTime);
    Optional<Transaction> findByCustomerUsernameAndConfirmedFalse(String username);
}
