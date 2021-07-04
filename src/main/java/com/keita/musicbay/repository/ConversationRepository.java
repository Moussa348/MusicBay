package com.keita.musicbay.repository;

import com.keita.musicbay.model.Conversation;
import com.keita.musicbay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    @Transactional
    @Query("SELECT c FROM Conversation c GROUP BY :user")
    List<Conversation> getByUser(@Param("user")User user);

}
