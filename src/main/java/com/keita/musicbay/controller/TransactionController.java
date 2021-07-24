package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.TransactionDTO;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.service.TransactionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:5001")
@Log
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/checkIfTransactionPending/{username}")
    public boolean checkIfTransactionPending(@PathVariable String username){
        return transactionService.checkIfTransactionPending(username);
    }

    @GetMapping("/checkIfArticleIsInTransaction/")
    public boolean checkIfArticleIsInTransaction(@RequestParam("username") String username,@RequestParam("title") String title){
        return transactionService.checkIfArticleIsInTransaction(username,title);
    }

    @PostMapping("/createTransaction")
    public TransactionDTO createTransaction(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("priceType") String priceType){
        return transactionService.createTransaction(username,title,PriceType.valueOf(priceType.toUpperCase()));
    }

    @PatchMapping("/addArticleToTransaction")
    public TransactionDTO addArticleToTransaction(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("priceType") String priceType){
        return transactionService.addArticleToTransaction(username,title,PriceType.valueOf(priceType.toUpperCase()));
    }

    @DeleteMapping("/removeArticleFromTransaction")
    public TransactionDTO removeArticleFromTransaction(@RequestParam("username") String username,@RequestParam("title") String title){
        return transactionService.removeArticleFromTransaction(username,title);
    }

    @DeleteMapping("/cancelTransaction")
    public void cancelTransaction(@RequestParam("username") String username, @RequestParam("uuid")UUID uuid) throws Exception{
        transactionService.cancelTransaction(username,uuid);
    }

    @GetMapping("/confirmTransaction/")
    public void confirmTransaction(@RequestParam("username") String username,@RequestParam("uuid") UUID uuid){
        try {
            transactionService.confirmTransaction(username,uuid);
        } catch (Exception e) {
            log.warning(e.getMessage().toUpperCase());
        }
    }

    @GetMapping("/getCurrentTransaction/{username}")
    public TransactionDTO getCurrentTransaction(@PathVariable String username){
        return transactionService.getCurrentTransaction(username);
    }
}
