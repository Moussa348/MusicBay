package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Liking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LikingRepository extends JpaRepository<Liking,Long> {
    List<Liking> getAllByCustomerUsername(String username,Pageable pageable);
}
