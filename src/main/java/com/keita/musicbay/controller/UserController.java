package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.Profile;
import com.keita.musicbay.model.dto.ProfileToSubscribeTo;
import com.keita.musicbay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getProfile/{username}")
    public Profile getProfile(@PathVariable String username){
        return userService.getProfile(username);
    }

    @GetMapping("/getPicture/{username}")
    public void getPicture(@PathVariable String username, HttpServletResponse httpServletResponse) throws Exception{
        userService.getPicture(username,httpServletResponse);
    }

    @GetMapping("/getListProfileSearch/")
    public List<ProfileToSubscribeTo> getListProfileSearch() {
        return userService.getListProfileSearch();
    }
}
