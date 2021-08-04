package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class MonitoringService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MusicService musicService;


    public void likeMusic(String username, String title) {
        Customer customer = getCustomerByUsername(username);
        Music music = musicRepository.findByTitle(title).get();

        musicService.increaseLike(music);

        customer.getLikings().add(new Liking(customer, music));

        customerRepository.save(customer);

        notificationService.saveNotification(customer.getUsername(), NotificationEvent.LIKING, customer.getSubscribers().stream().map(Subscriber::getUsername).collect(Collectors.toList()));
    }

    public void unLikeMusic(String username, String title) {
        Customer customer = getCustomerByUsername(username);
        Music music = musicRepository.findByTitle(title).get();

        customer.getLikings().removeIf(liking -> liking.getMusic().getTitle().equals(title));

        musicService.decreaseLike(music);

        customerRepository.saveAndFlush(customer);
    }

    public void shareMusic(String username, String title, String sharingMsg) {
        Customer customer = getCustomerByUsername(username);
        Music music = musicRepository.findByTitle(title).get();

        musicService.increaseShare(music);

        customer.getSharings().add(new Sharing(customer, music, sharingMsg));

        customerRepository.save(customer);

        notificationService.saveNotification(customer.getUsername(), NotificationEvent.SHARING, customer.getSubscribers().stream().map(Subscriber::getUsername).collect(Collectors.toList()));
    }

    public void unShareMusic(String username, String title) {
        Customer customer = getCustomerByUsername(username);
        Music music = musicRepository.findByTitle(title).get();

        customer.getSharings().removeIf(sharing -> sharing.getMusic().getTitle().equals(title));

        musicService.decreaseShare(music);

        customerRepository.save(customer);
    }

    public void purchaseMusic(Customer customer, String title, LocalDateTime purchasingDate) {
        Music music = musicRepository.findByTitle(title).get();

        musicService.increasePurchase(music);

        customer.getPurchasings().add(new Purchasing(customer, music, purchasingDate));

        customerRepository.saveAndFlush(customer);

        notificationService.saveNotification(customer.getUsername(), NotificationEvent.PURCHASING, Arrays.asList(music.getProducer().getUsername()));
    }

    @Transactional
    public void subscribe(String username, String usernameToFollow) {
        User user = getUserByUsername(username);

        if (user.getSubscribeTos().stream().noneMatch(subscribeTo -> subscribeTo.getUsername().equals(usernameToFollow))) {

            User userToFollow = getUserByUsername(usernameToFollow);

            user.getSubscribeTos().add(new SubscribeTo(userToFollow.getUsername(), user));
            userToFollow.getSubscribers().add(new Subscriber(user.getUsername(), userToFollow));

            userRepository.saveAll(Arrays.asList(user, userToFollow));

            notificationService.saveNotification(user.getUsername(), NotificationEvent.SUBSCRIPTION, Arrays.asList(userToFollow.getUsername()));
        }
    }

    @Transactional
    public void unSubscribe(String username, String usernameToUnFollow) {
        User user = getUserByUsername(username);
        User userToUnFollow = getUserByUsername(usernameToUnFollow);

        user.getSubscribeTos().removeIf(subscribeTo -> subscribeTo.getUsername().equals(usernameToUnFollow));
        userToUnFollow.getSubscribers().removeIf(subscriber -> subscriber.getUsername().equals(username));

        userRepository.saveAll(Arrays.asList(user, userToUnFollow));
    }

    public boolean checkIfSubscribeTo(String username, String usernameSubscribeTo) {
        User user = getUserByUsername(username);
        return user.getSubscribeTos().stream().anyMatch(subscribeTo -> subscribeTo.getUsername().equals(usernameSubscribeTo));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Customer with username: " + username));
    }

    private Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find Customer with username: " + username));
    }

}
