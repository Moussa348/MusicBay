package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    void addArticleInTransaction(){
        //ARRANGE
        Transaction currentTransaction = Transaction.builder().total(24.5f).customer(Customer.builder().username("bigBrr").build()).build();
        currentTransaction.getArticles().add(Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(24.5f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build());

        Music music = Track.builder().title("frrr").basicPrice(25.4f).build();
        when(musicRepository.findByTitle(music.getTitle())).thenReturn(Optional.of(music));

        //ACT
        Transaction currentTransactionWithAddedArticle = articleService.addArticleInTransaction(currentTransaction,music.getTitle(),PriceType.BASIC);

        //ASSERT
        assertEquals(2,currentTransactionWithAddedArticle.getArticles().size());
    }

    @Test
    void removeArticleFromTransaction(){
        //ARRANGE
        Transaction currentTransaction = Transaction.builder().total(25.0f).customer(Customer.builder().username("bigBrr").build()).build();
        currentTransaction.getArticles().add(Article.builder().music(MixTape.builder().title("hoodSeason").basicPrice(25.0f).exclusivePrice(30.0f).build()).priceType(PriceType.BASIC).build());

        //ACT
        Transaction currentTransactionWithRemovedArticle = articleService.removeArticleFromTransaction(currentTransaction,currentTransaction.getArticles().get(0).getMusic().getTitle());

        //ASSERT
        assertEquals(0,currentTransactionWithRemovedArticle.getArticles().size());
        assertEquals(0,currentTransactionWithRemovedArticle.getTotal());
    }
}
