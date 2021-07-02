package com.keita.musicbay.controller;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public boolean createCustomer(@RequestBody Customer customer, @RequestParam("picture")MultipartFile multipartFile) throws Exception{
        return customerService.createCustomer(customer,multipartFile);
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
