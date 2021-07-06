package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.Purchasing;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PurchasedMusic implements Serializable {
    private LocalDateTime purchasingDate;
    private MusicDTO music;

    public PurchasedMusic(){}

    public PurchasedMusic(Purchasing purchasing){
        this.purchasingDate = purchasing.getPurchasingDate();
        this.music = new MusicDTO(purchasing.getMusic());
    }
}
