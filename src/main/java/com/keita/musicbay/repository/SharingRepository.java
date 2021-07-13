package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Sharing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SharingRepository extends JpaRepository<Sharing,Long> {
    List<Sharing> getByCustomerAndSharingDateBetween(Customer customer, LocalDateTime date1,LocalDateTime date2);
    List<Sharing> getAllByCustomer(Customer customer, Pageable pageable);
}
