package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Purchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchasingRepository extends JpaRepository<Purchasing,Long> {
    List<Purchasing> getByCustomerAndPurchasingDateBetween(Customer customer, LocalDateTime date1,LocalDateTime date2);
}
