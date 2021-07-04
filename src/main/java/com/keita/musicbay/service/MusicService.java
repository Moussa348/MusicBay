package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Catalog;
import com.keita.musicbay.model.dto.MusicDTO;
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
        return new Catalog(customerRepository.findByUserName(username).get(),musicRepository.findAll());
    }

    @Transactional
    public List<MusicDTO> getListMusic(){
        return musicRepository.findAll().stream().map(MusicDTO::new).collect(Collectors.toList());
    }

}
