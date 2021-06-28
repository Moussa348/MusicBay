package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    Optional<Customer> findByUserName(String username);

}
