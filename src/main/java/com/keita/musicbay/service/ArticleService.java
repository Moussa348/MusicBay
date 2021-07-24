package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Transaction;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    public Transaction removeArticleFromTransaction(Transaction currentTransaction, String title) {
        Article articleToRemove = currentTransaction.getArticles().stream().filter(article -> article.getMusic().getTitle().equals(title)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find article with music title : " + title));

        currentTransaction.setTotal(currentTransaction.getTotal() - articleToRemove.getPrice());

        currentTransaction.getArticles().remove(articleToRemove);

        return currentTransaction;
    }

    public List<MusicArticle> getListMusicArticle(Transaction transaction){
        return transaction.getArticles().stream().map(MusicArticle::new).collect(Collectors.toList());
    }
}
