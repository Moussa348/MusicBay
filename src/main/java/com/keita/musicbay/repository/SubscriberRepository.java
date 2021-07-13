package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber,Long> {
    List<Subscriber> getByUserUsername(String username);
}
