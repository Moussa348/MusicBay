package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MusicRepository extends JpaRepository<Music, UUID> {
    boolean existsByTitle(String title);
    Optional<Music> findByTitle(String title);
    Page<Music> getAllBy(Pageable pageable);
    double countAllBy();
}
