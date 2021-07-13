package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Sharing;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SharedMusic implements Serializable {
    private String sharingMsg,sharedBy;
    private LocalDateTime sharingDate;
    private MusicDTO music;

    public SharedMusic(){}

    public SharedMusic(Sharing sharing){
        this.sharedBy = sharing.getCustomer().getUsername();
        this.sharingDate = sharing.getSharingDate();
        this.sharingMsg = sharing.getSharingMsg();
        this.music = new MusicDTO(sharing.getMusic());
    }
}
