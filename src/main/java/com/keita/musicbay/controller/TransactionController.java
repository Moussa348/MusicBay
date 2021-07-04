package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:5001")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/createTransaction")
    public TransactionDTO createTransaction(@RequestParam("username") String username,@RequestParam("title") String title){
        return transactionService.createTransaction(username,title);
    }

    @PatchMapping("/addMusicToTransaction")
    public TransactionDTO addMusicToTransaction(@RequestParam("username") String username,@RequestParam("title") String title){
        return transactionService.addMusicToTransaction(username,title);
    }

    @DeleteMapping("/removeMusicFromTransaction")
    public TransactionDTO removeMusicFromTransaction(@RequestParam("username") String username,@RequestParam("title") String title){
        return transactionService.removeMusicFromTransaction(username,title);
    }

    @DeleteMapping("/cancelTransaction")
    public void cancelTransaction(@RequestParam("username") String username, @RequestParam("uuid")UUID uuid){
        transactionService.cancelTransaction(username,uuid);
    }

    @GetMapping("/getCurrentTransaction/{username}")
    public TransactionDTO getCurrentTransaction(@PathVariable String username){
        return transactionService.getCurrentTransaction(username);
    }
}
