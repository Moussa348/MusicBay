package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Subscriber;
import com.keita.musicbay.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber,Long> {
    List<Subscriber> getAllByUserUsername(String username,Pageable pageable);
}
