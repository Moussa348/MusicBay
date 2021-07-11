package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.Catalog;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/music")
@CrossOrigin(origins = "http://localhost:5001")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/getMusic/{title}")
    public MusicDTO getMusic(@PathVariable String title){
        return musicService.getMusic(title);
    }

    @PermitAll()
    @GetMapping("/getCatalog/{username}")
    public Catalog getCatalog(@PathVariable String username){
        return musicService.getCatalog(username);
    }
}
