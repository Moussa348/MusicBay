package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Music;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Catalog implements Serializable {
    private List<String> likedMusicTitles;
    private List<String> sharedMusicTitles;
    private List<MusicDTO> musics;

    public Catalog(){}

    public Catalog(Customer customer, List<Music> musics){
        this.likedMusicTitles = customer.getLikings().stream().map(liking -> liking.getMusic().getTitle()).collect(Collectors.toList());
        this.sharedMusicTitles = customer.getSharings().stream().map(sharing -> sharing.getMusic().getTitle()).collect(Collectors.toList());
        this.musics = musics.stream().map(MusicDTO::new).collect(Collectors.toList());
    }
}
