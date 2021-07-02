package com.keita.musicbay.controller;

import com.keita.musicbay.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @PostMapping("/likeMusic")
    public void likeMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.likeMusic(username,title);
    }

    @DeleteMapping("/unLikeMusic")
    public void unLikeMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.unLikeMusic(username,title);
    }

    @PostMapping("/shareMusic")
    public void shareMusic(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("sharingMsg") String sharingMsg){
        monitoringService.shareMusic(username,title,sharingMsg);
    }

    @DeleteMapping("/unShareMusic")
    public void unShareMusic(@RequestParam("username") String username,@RequestParam("title") String title){
        monitoringService.unShareMusic(username,title);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam("username") String username,@RequestParam("usernameToFollow") String usernameToFollow){
        monitoringService.subscribe(username,usernameToFollow);
    }

    @DeleteMapping("/unSubscribe")
    public void unSubscribe(@RequestParam("username") String username,@RequestParam("usernameToUnFollow") String usernameToUnFollow){
        monitoringService.unSubscribe(username,usernameToUnFollow);
    }


}
