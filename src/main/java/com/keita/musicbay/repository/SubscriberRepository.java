package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Subscriber;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends PagingAndSortingRepository<Subscriber,Long> {
    List<Subscriber> getByUserUsername(String username);
}
