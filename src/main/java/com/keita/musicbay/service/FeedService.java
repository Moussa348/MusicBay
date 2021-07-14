package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
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


    public Feed getFeed(String username, Integer noPage) {
        List<Liking> likings = new ArrayList<>();
        List<Sharing> sharings = new ArrayList<>();
        List<Purchasing> purchasings = new ArrayList<>();

        Customer customer = getCustomerByUsername(username);
        List<Customer> listCustomersThatYouAreSubscribeTo = customer.getSubscribeTos().stream().map(subscribeTo -> customerRepository.findByUsername(subscribeTo.getUsername()).get()).collect(Collectors.toList());

        listCustomersThatYouAreSubscribeTo.forEach(customerThatYouAreSubscribeTo -> {
            likings.addAll(likingRepository.getAllByCustomer(customerThatYouAreSubscribeTo, PageRequest.of(noPage, 5, Sort.by("likingDate").descending())));
            sharings.addAll(sharingRepository.getAllByCustomer(customerThatYouAreSubscribeTo, PageRequest.of(noPage, 5, Sort.by("sharingDate").descending())));
            purchasings.addAll(purchasingRepository.getAllByCustomer(customerThatYouAreSubscribeTo, PageRequest.of(noPage, 5, Sort.by("purchasingDate").descending())));
        });

        return new Feed(likings, sharings, purchasings);
    }


    public List<ProfileToSubscribeTo> getListPossibleSubscribeTo(String username, Integer noPage) {
        User user = getUserByUsername(username);
        List<User> listPossibleUserToSubscribeTo = userRepository.getAllByUsernameNot(username, PageRequest.of(noPage, 10, Sort.by("username")));

        listPossibleUserToSubscribeTo = listPossibleUserToSubscribeTo
                .stream()
                .filter(possibleUserToSubscribeTo -> user.getSubscribeTos().stream().noneMatch(subscribeTo -> possibleUserToSubscribeTo.getUsername().equals(subscribeTo.getUsername())))
                .collect(Collectors.toList());

        return listPossibleUserToSubscribeTo.stream().map(ProfileToSubscribeTo::new).collect(Collectors.toList());
    }

    public List<LikedMusic> getListLikedMusic(String username,Integer noPage) {
        Customer customer = getCustomerByUsername(username);
        return likingRepository.getAllByCustomer(customer,PageRequest.of(noPage, 5, Sort.by("likingDate").descending())).stream().map(LikedMusic::new).collect(Collectors.toList());
    }

    public List<SharedMusic> getListSharedMusic(String username,Integer noPage) {
        Customer customer = getCustomerByUsername(username);
        return sharingRepository.getAllByCustomer(customer,PageRequest.of(noPage, 5, Sort.by("sharingDate").descending())).stream().map(SharedMusic::new).collect(Collectors.toList());
    }

    public List<PurchasedMusic> getListPurchasedMusic(String username, Integer noPage) {
        Customer customer = getCustomerByUsername(username);
        return purchasingRepository.getAllByCustomer(customer,PageRequest.of(noPage, 5, Sort.by("purchasingDate").descending())).stream().map(PurchasedMusic::new).collect(Collectors.toList());
    }

    public List<Profile> getListSubscriber(String username,Integer noPage) {
        User user = getUserByUsername(username);
        List<User> users = subscriberRepository.getAllByUser(user,PageRequest.of(noPage,10, Sort.by("date").descending()))
                .stream()
                .map(subscriber -> userRepository.findByUsername(subscriber.getUsername()).get())
                .collect(Collectors.toList());

        return mapListUserToListProfile(users);
    }

    public List<Profile> getListSubscribeTo(String username,Integer noPage) {
        User user = getUserByUsername(username);
        List<User> users = subscribeToRepository.getAllByUser(user,PageRequest.of(noPage,10, Sort.by("date").descending()))
                .stream()
                .map(subscriberTo -> userRepository.findByUsername(subscriberTo.getUsername()).get())
                .collect(Collectors.toList());

        return  mapListUserToListProfile(users);

    }

    private User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with username: " + username));
    }

    private Customer getCustomerByUsername(String username){
        return customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with username: " + username));
    }

    private List<Profile> mapListUserToListProfile(List<User> users){
        return  users.stream().filter(User::isActive).map(Profile::new).collect(Collectors.toList());
    }

}
