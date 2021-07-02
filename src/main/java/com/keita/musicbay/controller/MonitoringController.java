package com.keita.musicbay.controller;

import com.keita.musicbay.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @PostMapping("/likeMusic")
    public void likeMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.likeMusic(username,title);
    }

    @PostMapping("/unLikeMusic")
    public void unLikeMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.unLikeMusic(username,title);
    }

    @PostMapping("/shareMusic")
    public void shareMusic(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("sharingMsg") String sharingMsg){
        monitoringService.shareMusic(username,title,sharingMsg);
    }

    @PostMapping("/unShareMusic")
    public void unShareMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.unShareMusic(username,title);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam("username") String username,@RequestParam("usernameToFollow") String usernameToFollow){
        monitoringService.subscribe(username,usernameToFollow);
    }

    @PostMapping("/unSubscribe")
    public void unSubscribe(@RequestParam("username") String username,@RequestParam("usernameToUnFollow") String usernameToUnFollow){
        monitoringService.unSubscribe(username,usernameToUnFollow);
    }


}
