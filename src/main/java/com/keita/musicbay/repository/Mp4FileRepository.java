package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Mp4File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp4FileRepository extends JpaRepository<Mp4File,Long> {
}
