package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.SubscribeTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeToRepository extends JpaRepository<SubscribeTo,Long> {
    List<SubscribeTo> getByUserUsername(String username);
}
