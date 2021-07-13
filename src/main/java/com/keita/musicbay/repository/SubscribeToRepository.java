package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.SubscribeTo;
import com.keita.musicbay.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeToRepository extends JpaRepository<SubscribeTo,Long> {
    List<SubscribeTo> getByUserUsername(String username);
    List<SubscribeTo> getAllByUser(User user, Pageable pageable);
}
