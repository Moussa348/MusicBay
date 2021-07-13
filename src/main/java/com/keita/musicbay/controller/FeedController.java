package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.model.dto.ProfileToSubscribeTo;
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
}
