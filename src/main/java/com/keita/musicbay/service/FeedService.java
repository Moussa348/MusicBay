package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Feed;
import com.keita.musicbay.model.dto.Profile;
import com.keita.musicbay.model.dto.ProfileToSubscribeTo;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Feed getFeed(String username,Integer noPage){
        List<Liking> likings = new ArrayList<>();
        List<Sharing> sharings = new ArrayList<>();
        List<Purchasing> purchasings = new ArrayList<>();

        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer with username: " + username));
        List<Customer> listCustomersThatYouAreSubscribeTo = customer.getSubscribeTos().stream().map(subscribeTo -> customerRepository.findByUsername(subscribeTo.getUsername()).get()).collect(Collectors.toList());

        listCustomersThatYouAreSubscribeTo.forEach(customerThatYouAreSubscribeTo ->{
            likings.addAll(likingRepository.getAllByCustomer(customerThatYouAreSubscribeTo,PageRequest.of(noPage,2,Sort.by("likingDate").descending())));
            sharings.addAll(sharingRepository.getAllByCustomer(customerThatYouAreSubscribeTo,PageRequest.of(noPage,2,Sort.by("sharingDate").descending())));
            purchasings.addAll(purchasingRepository.getAllByCustomer(customerThatYouAreSubscribeTo,PageRequest.of(noPage,2,Sort.by("purchasingDate").descending())));
        });
        return new Feed(likings,sharings,purchasings);
    }


    public List<ProfileToSubscribeTo> getListPossibleSubscribeTo(String username, Integer noPage) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username: " + username));
        List<User> listPossibleUserToSubscribeTo = userRepository.getAllByUsernameNot(username,PageRequest.of(noPage, 5, Sort.by("username")));

        listPossibleUserToSubscribeTo = listPossibleUserToSubscribeTo
                .stream()
                .filter(possibleUserToSubscribeTo -> user.getSubscribeTos().stream().noneMatch(subscribeTo -> possibleUserToSubscribeTo.getUsername().equals(subscribeTo.getUsername())))
                .collect(Collectors.toList());

        return listPossibleUserToSubscribeTo.stream().map(ProfileToSubscribeTo::new).collect(Collectors.toList());
    }

}
