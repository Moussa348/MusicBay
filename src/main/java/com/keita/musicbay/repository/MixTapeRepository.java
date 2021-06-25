package com.keita.musicbay.repository;

import com.keita.musicbay.model.MixTape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MixTapeRepository extends JpaRepository<MixTape, UUID> {
}
