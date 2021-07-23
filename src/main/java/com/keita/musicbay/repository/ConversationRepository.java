package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    @Query("SELECT c FROM Conversation c GROUP BY :user,c.id")
    List<Conversation> getByUser(@Param("user")User user, Pageable pageable);

}
