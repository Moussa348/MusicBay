package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Feed getFeed(String username, LocalDateTime date){
        Customer customer = customerRepository.findByUsername(username).get();
        List<Liking> likings = likingRepository.getByCustomerAndLikingDateBetween(customer,date.minusDays(5),date);
        List<Sharing> sharings = sharingRepository.getByCustomerAndSharingDateBetween(customer,date.minusDays(5),date);
        List<Purchasing> purchasings = purchasingRepository.getByCustomerAndPurchasingDateBetween(customer,date.minusDays(5),date);

        /*
            TODO:
                -create an algorithm to be able
                    --> every subscribers of user get list subscribeTo and subscribers that the user is not already subscribeTo
                    ---> same for every subscribeTos of user
                    ----> and then map all of it as list user and constructor will mapped them as profiles
         */

        return new Feed(likings,sharings,purchasings, Collections.emptyList());
    }

}
