package com.keita.musicbay.repository;

import com.keita.musicbay.model.WavFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WavFileRepository extends JpaRepository<WavFile,Long> {
}
