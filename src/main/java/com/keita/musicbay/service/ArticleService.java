package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Transaction;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ArticleService {

    @Autowired
    private MusicRepository musicRepository;

    public Transaction addArticleInTransaction(Transaction currentTransaction,String title,PriceType priceType) {
        Article article = new Article(priceType,currentTransaction,musicRepository.findByTitle(title).get());

        currentTransaction.getArticles().add(article);
        currentTransaction.setTotal(currentTransaction.getTotal() + article.getPrice());

        return currentTransaction;
    }
}
