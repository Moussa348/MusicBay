package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Comment;
import com.keita.musicbay.model.entity.Music;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> getAllByMusic(Music music, Pageable pageable);
}
