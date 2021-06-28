package com.keita.musicbay.controller;

import com.keita.musicbay.service.CustomerActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerActivity")
public class CustomerActivityController {

    @Autowired
    private CustomerActivityService customerActivityService;

    @PostMapping("/likeMusic")
    public void likeMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        customerActivityService.likeMusic(username,title);
    }

    @PostMapping("/shareMusic")
    public void shareMusic(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("sharingMsg") String sharingMsg){
        customerActivityService.shareMusic(username,title,sharingMsg);
    }

}
