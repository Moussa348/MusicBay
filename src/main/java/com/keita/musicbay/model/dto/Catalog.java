package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Music;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Catalog implements Serializable {
    private List<String> likedMusicTitles;
    private List<String> sharedMusicTitles;
    private List<MusicDTO> musics;

    public Catalog(){}

    public Catalog(Optional<Customer> customer, List<Music> musics){
        this.likedMusicTitles = customer.map(value -> value.getLikings().stream().map(liking -> liking.getMusic().getTitle()).collect(Collectors.toList())).orElse(Collections.emptyList());
        this.sharedMusicTitles = customer.map(value -> value.getSharings().stream().map(sharing -> sharing.getMusic().getTitle()).collect(Collectors.toList())).orElse(Collections.emptyList());
        this.musics = musics.stream().map(MusicDTO::new).collect(Collectors.toList());
    }
}
