package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> getByUserUsernameAndDateBetween(String username, LocalDateTime date1,LocalDateTime date2);
}
