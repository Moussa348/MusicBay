package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Catalog;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public MusicDTO getMusic(String title){
        return musicRepository.findByTitle(title).map(MusicDTO::new).orElse(null);
    }

    @Transactional
    public Catalog getCatalog(String username){
        return new Catalog(customerRepository.findByUsername(username),musicRepository.findAll());
    }

    @Transactional
    public List<MusicDTO> getListMusic(){
        return musicRepository.findAll().stream().map(MusicDTO::new).collect(Collectors.toList());
    }

    public void increaseLike(Music music) {
        music.setNbrOfLike(music.getNbrOfLike() + 1);
        musicRepository.saveAndFlush(music);
    }

    public void decreaseLike(Music music) {
        int nbrLike = music.getNbrOfLike();

        if (nbrLike > 0) {
            music.setNbrOfLike(nbrLike - 1);
            musicRepository.saveAndFlush(music);
        }
    }

    public void increaseShare(Music music) {
        music.setNbrOfShare(music.getNbrOfShare() + 1);
        musicRepository.saveAndFlush(music);
    }

    public void decreaseShare(Music music) {
        int nbrShare = music.getNbrOfShare();

        if (nbrShare > 0) {
            music.setNbrOfShare(nbrShare - 1);
            musicRepository.saveAndFlush(music);
        }
    }

    public void increasePurchase(Music music) {
        music.setNbrOfPurchase(music.getNbrOfPurchase() + 1);
        musicRepository.saveAndFlush(music);
    }


}
