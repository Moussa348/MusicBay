package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/getMusic/{title}")
    public MusicDTO getMusic(@PathVariable String title){
        return musicService.getMusic(title);
    }

    @GetMapping("/getListMusic")
    public List<MusicDTO> getListMusic(){
        return musicService.getListMusic();
    }
}
