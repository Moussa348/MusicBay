package com.keita.musicbay.repository;

import com.keita.musicbay.model.PurchasedMusicArticle;
import com.keita.musicbay.model.SharedMusicArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchasedMusicArticleRepository extends JpaRepository<PurchasedMusicArticle, UUID> {
}
