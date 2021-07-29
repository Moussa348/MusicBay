package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Catalog;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return musicRepository.findByTitle(title).map(MusicDTO::new).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with username: " + title));
    }

    @Transactional
    public Catalog getCatalog(String username,Integer noPage){
        return new Catalog(customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with username: " + username)),
                musicRepository.findAll(PageRequest.of(noPage, 30, Sort.by("date").descending())).getContent());
    }

    @Transactional
    public List<MusicDTO> getListMusic(Integer noPage){
        return musicRepository.findAll(PageRequest.of(noPage, 30, Sort.by("date").descending())).stream().map(MusicDTO::new).collect(Collectors.toList());
    }

    public Integer getNbrOfPage(){
        return musicRepository.getAllBy(PageRequest.of(0,30)).getTotalPages();
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
