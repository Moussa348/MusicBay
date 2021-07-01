package com.keita.musicbay.repository;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    List<Conversation> getByUser(User user);
}
