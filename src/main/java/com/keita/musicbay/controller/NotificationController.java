package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.RecentNotification;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "http://localhost:5001")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @PatchMapping("/notificationSeen/{id}")
    public void notificationSeen(@PathVariable Long id){
        notificationService.notificationSeen(id);
    }

    @GetMapping("/getRecentNotifications")
    public List<RecentNotification> getRecentNotifications(@RequestParam("username")String username,@RequestParam("noPage")Integer noPage ){
        return notificationService.getRecentNotifications(username,noPage);
    }

    @GetMapping("/getNbrOfPage/{username}")
    public Integer getNbrOfPage(@PathVariable String username){
        return notificationService.getNbrOfPage(username);
    }


}
