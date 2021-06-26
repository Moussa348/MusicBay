package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MusicRepository musicRepository;

    public void likeMusic(String username, String title){
        Customer customer = customerRepository.findByUserName(username).get();
        LikedMusic likedMusic = (LikedMusic) musicRepository.findByTitle(title).get();

        customer.getLikedMusics().add(likedMusic);

        customerRepository.save(customer);
    }



    public void shareMusic(String username, String title){
        Customer customer = customerRepository.findByUserName(username).get();
        SharedMusic sharedMusic = (SharedMusic) musicRepository.findByTitle(title).get();

        customer.getSharedMusics().add(sharedMusic);

        customerRepository.save(customer);
    }

    public void purchaseMusic(String username, String title){
        Customer customer = customerRepository.findByUserName(username).get();
        PurchasedMusic purchasedMusic = (PurchasedMusic) musicRepository.findByTitle(title).get();

        customer.getPurchasedMusics().add(purchasedMusic);

        customerRepository.save(customer);
    }
}
