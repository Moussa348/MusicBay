package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findByUsername(String username);
   List<User> getAllByUsernameNot(String username, Pageable pageable);
   double countAllByUsernameNot(String username);
}
