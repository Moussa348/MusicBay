package com.keita.musicbay.repository;

import com.keita.musicbay.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicRepository extends JpaRepository<Music, UUID> {
}