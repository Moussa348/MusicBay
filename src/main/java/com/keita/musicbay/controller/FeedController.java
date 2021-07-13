package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/feed")
@CrossOrigin(origins = "http://localhost:5001")
public class FeedController {

    @Autowired
    private FeedService feedService;


    @GetMapping("/getFeed")
    public Feed getFeed(@RequestParam("username")String username,@RequestParam("date")String date,@RequestParam("nbrOfDays")Integer nbrOfDays ){
        return feedService.getFeed(username,date,nbrOfDays);
    }
}
