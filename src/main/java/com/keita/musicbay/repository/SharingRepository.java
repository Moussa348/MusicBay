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
    List<Sharing> getAllByCustomerUsername(String username,Pageable pageable);
}
