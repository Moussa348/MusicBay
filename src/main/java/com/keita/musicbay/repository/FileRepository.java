package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {
    boolean existsByFileName(String fileName);
    Optional<File> findByFileName(String fileName);
}
