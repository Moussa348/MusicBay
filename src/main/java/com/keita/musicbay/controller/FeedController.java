package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = "http://localhost:5001")
public class FeedController {

    @Autowired
    private FeedService feedService;


    @GetMapping("/getFeed")
    public Feed getFeed(@RequestParam("username")String username,@RequestParam("noPage")Integer noPage ){
        return feedService.getFeed(username,noPage);
    }

    @GetMapping("/getListPossibleSubscribeTo")
    public List<ProfileToSubscribeTo> getListPossibleSubscribeTo(@RequestParam("username") String username,@RequestParam("noPage") Integer noPage){
        return feedService.getListPossibleSubscribeTo(username,noPage);
    }

    @GetMapping("/getListLikedMusic")
    public List<LikedMusic> getListLikedMusic(@RequestParam("username") String username,@RequestParam("noPage") Integer noPage){
        return feedService.getListLikedMusic(username,noPage);
    }

    @GetMapping("/getListSharedMusic")
    public List<SharedMusic> getListSharedMusic(@RequestParam("username") String username, @RequestParam("noPage") Integer noPage){
        return feedService.getListSharedMusic(username,noPage);
    }

    @GetMapping("/getListPurchasedMusic")
    public List<PurchasedMusic> getListPurchasedMusic(@RequestParam("username") String username, @RequestParam("noPage") Integer noPage){
        return feedService.getListPurchasedMusic(username,noPage);
    }

    @GetMapping("/getListSubscriber")
    public List<Profile> getListSubscriber(@RequestParam("username") String username, @RequestParam("noPage") Integer noPage){
        return feedService.getListSubscriber(username,noPage);
    }

    @GetMapping("/getListSubscribeTo")
    public List<Profile> getListSubscribeTo(@RequestParam("username") String username, @RequestParam("noPage") Integer noPage){
        return feedService.getListSubscribeTo(username,noPage);
    }
}
