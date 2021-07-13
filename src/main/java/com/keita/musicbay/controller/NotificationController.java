package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.RecentNotification;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getRecentNotifications")
    public List<RecentNotification> getRecentNotifications(@RequestParam("username")String username,@RequestParam("noPage")Integer noPage ){
        return notificationService.getRecentNotifications(username,noPage);
    }
}
