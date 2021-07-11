package com.keita.musicbay.controller;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:5001")
@Log
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    @PermitAll
    public boolean createCustomer(@RequestBody Registration registration) throws Exception{
        return customerService.createCustomer(registration);
    }

    @PatchMapping( "/updateCustomer")
    public Profile updateCustomer(@RequestBody Registration registration) {
        try {
            return customerService.updateCustomer(registration);
        } catch (Exception e) {
            log.warning(e.getMessage().toUpperCase());
            return null;
        }
    }

    @GetMapping("/getProfile/{username}")
    public Profile getProfile(@PathVariable String username){
        return customerService.getProfile(username);
    }

    @GetMapping("/getPicture/{username}")
    public void getPicture(@PathVariable String username, HttpServletResponse httpServletResponse) throws Exception{
        customerService.getPicture(username,httpServletResponse);
    }

    @GetMapping("/getListLikedMusic/{username}")
    public List<LikedMusic> getListLikedMusic(@PathVariable String username){
        return customerService.getListLikedMusic(username);
    }

    @GetMapping("/getListSharedMusic/{username}")
    public List<SharedMusic> getListSharedMusic(@PathVariable String username){
        return customerService.getListSharedMusic(username);
    }

    @GetMapping("/getListPurchasedMusic/{username}")
    public List<PurchasedMusic> getListPurchasedMusic(@PathVariable String username){
        return customerService.getListPurchasedMusic(username);
    }

    @GetMapping("/getListSubscriber/{username}")
    public List<Profile> getListSubscriber(@PathVariable String username){
        return customerService.getListSubscriber(username);
    }

    @GetMapping("/getListSubscribeTo/{username}")
    public List<Profile> getListSubscribeTo(@PathVariable String username){
        return customerService.getListSubscribeTo(username);
    }
}
