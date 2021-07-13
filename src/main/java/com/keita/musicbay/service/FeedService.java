package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LikingRepository likingRepository;

    @Autowired
    private SharingRepository sharingRepository;

    @Autowired
    private PurchasingRepository purchasingRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscribeToRepository subscribeToRepository;


    //Take 3 argument username,String date, nbrOfDays
    public Feed getFeed(String username,String date,Integer nbrOfDays){
        LocalDateTime todayDate = LocalDateTime.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    /*


        List<Liking> likings = new ArrayList<>();
        List<Sharing> sharings = new ArrayList<>();
        List<Purchasing> purchasings = new ArrayList<>();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer with username: " + username));

        List<Customer> listCustomersThatYouAreSubscribeTo = customer.getSubscribeTos().stream().map(subscribeTo -> customerRepository.findByUsername(subscribeTo.getUsername()).get()).collect(Collectors.toList());

        listCustomersThatYouAreSubscribeTo.forEach(customerThatYouAreSubscribeTo ->{
            likings.addAll(likingRepository.getByCustomerAndLikingDateBetween(customerThatYouAreSubscribeTo,todayDate.minusDays(nbrOfDays),todayDate));
            sharings.addAll(sharingRepository.getByCustomerAndSharingDateBetween(customerThatYouAreSubscribeTo,todayDate.minusDays(nbrOfDays),todayDate));
            purchasings.addAll(purchasingRepository.getByCustomerAndPurchasingDateBetween(customerThatYouAreSubscribeTo,todayDate.minusDays(nbrOfDays),todayDate));
        });
     */



                Customer customer = customerRepository.findByUsername(username).get();


        List<Liking> likings = likingRepository.getByCustomerAndLikingDateBetween(customer,todayDate.minusDays(nbrOfDays),todayDate);
        List<Sharing> sharings = sharingRepository.getByCustomerAndSharingDateBetween(customer,todayDate.minusDays(nbrOfDays),todayDate);
        List<Purchasing> purchasings = purchasingRepository.getByCustomerAndPurchasingDateBetween(customer,todayDate.minusDays(nbrOfDays),todayDate);

            /*TODO:
                -be able to fetch a limited qty every time
                -create an algorithm to be able
                    --> every subscribers of user get list subscribeTo and subscribers that the user is not already subscribeTo
                    ---> same for every subscribeTos of user
                    ----> and then map all of it as list user and constructor will mapped them as profiles

         */
        return new Feed(likings,sharings,purchasings, Collections.emptyList());
    }

}
