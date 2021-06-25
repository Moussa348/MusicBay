package com.keita.musicbay.repository;

import com.keita.musicbay.model.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp3FileRepository extends JpaRepository<Mp3File,Long> {
}
