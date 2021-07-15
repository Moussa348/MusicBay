package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> getAllByUserUsernameAndSeenFalse(String username,Pageable pageable);
}
