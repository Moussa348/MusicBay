package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Transaction;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class TransactionDTO implements Serializable {

    private UUID uuid;
    private LocalDateTime date;
    private List<MusicArticle> musicArticles;
    private Float total;

    public TransactionDTO(Transaction transaction){
        this.uuid = transaction.getUuid();
        this.date = transaction.getDate();
        this.total = transaction.getTotal();
        this.musicArticles = transaction.getMusics().stream().map(MusicArticle::new).collect(Collectors.toList());
    }
}
