package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> getAllByConversationId(Long id, Pageable pageable);
    List<Message> getAllByConversationIdAndConversationActiveTrue(Long id, Pageable pageable);
}
