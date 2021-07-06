package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Sharing;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SharedMusic implements Serializable {
    private String sharingMsg;
    private LocalDateTime sharingDate;
    private MusicDTO music;

    public SharedMusic(){}

    public SharedMusic(Sharing sharing){
        this.sharingDate = sharing.getSharingDate();
        this.sharingMsg = sharing.getSharingMsg();
        this.music = new MusicDTO(sharing.getMusic());
    }
}
