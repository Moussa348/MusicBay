package com.keita.musicbay.repository;

import com.keita.musicbay.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
