package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class MonitoringService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private NotificationService notificationService;


    public void likeMusic(String username, String title) {
        Customer customer = customerRepository.findByUsername(username).get();
        Music music = musicRepository.findByTitle(title).get();

        increaseLike(music);
        customer.getLikings().add(new Liking(customer, music));

        customerRepository.save(customer);

        notificationService.saveNotification(NotificationEvent.LIKING,customer);
    }

    public void unLikeMusic(String username, String title) {
        Customer customer = customerRepository.findByUsername(username).get();
        Music music = musicRepository.findByTitle(title).get();

         customer.getLikings().removeIf(liking -> liking.getMusic().getTitle().equals(title));
        decreaseLike(music);

        customerRepository.saveAndFlush(customer);
    }

    public void shareMusic(String username, String title, String sharingMsg) {
        Customer customer = customerRepository.findByUsername(username).get();
        Music music = musicRepository.findByTitle(title).get();

        increaseShare(music);
        customer.getSharings().add(new Sharing(customer, music, sharingMsg));

        customerRepository.save(customer);

        notificationService.saveNotification(NotificationEvent.SHARING,customer);
    }

    public void unShareMusic(String username, String title) {
        Customer customer = customerRepository.findByUsername(username).get();
        Music music = musicRepository.findByTitle(title).get();

        customer.getSharings().removeIf(sharing -> sharing.getMusic().getTitle().equals(title));
        decreaseShare(music);

        customerRepository.save(customer);
    }

    public void purchaseMusic(String username, String title, LocalDateTime purchasingDate) {
        Customer customer = customerRepository.findByUsername(username).get();
        Music music = musicRepository.findByTitle(title).get();

        increasePurchase(music);

        customer.getPurchasings().add(new Purchasing(customer, music, purchasingDate));

        customerRepository.save(customer);

        notificationService.saveNotification(NotificationEvent.PURCHASING,customer);
    }

    @Transactional
    public void subscribe(String username, String usernameToFollow) {
        Customer customer = customerRepository.findByUsername(username).get();
        Customer customerToFollow = customerRepository.findByUsername(usernameToFollow).get();

        customer.getSubscribeTos().add(new SubscribeTo(customerToFollow.getUsername(), customer));
        customerToFollow.getSubscribers().add(new Subscriber(customer.getUsername(), customerToFollow));

        customerRepository.saveAll(Arrays.asList(customer, customerToFollow));

        notificationService.saveNotification(NotificationEvent.SUBSCRIPTION,customerToFollow);
    }

    @Transactional
    public void unSubscribe(String username, String usernameToUnFollow) {
        Customer customer = customerRepository.findByUsername(username).get();
        Customer customerToUnFollow = customerRepository.findByUsername(usernameToUnFollow).get();

        customer.getSubscribeTos().removeIf(subscribeTo -> subscribeTo.getUsername().equals(usernameToUnFollow));
        customerToUnFollow.getSubscribers().removeIf(subscriber -> subscriber.getUsername().equals(username));

        customerRepository.saveAll(Arrays.asList(customer, customerToUnFollow));
    }

    public boolean checkIfSubscribeTo(String username,String usernameSubscribeTo){
        Customer customer = customerRepository.findByUsername(username).get();
        return customer.getSubscribeTos().stream().anyMatch(subscribeTo -> subscribeTo.getUsername().equals(usernameSubscribeTo));
    }

    private void increaseLike(Music music) {
        music.setNbrOfLike(music.getNbrOfLike() + 1);
        musicRepository.saveAndFlush(music);
    }

    private void decreaseLike(Music music) {
        int nbrLike = music.getNbrOfLike();

        if (nbrLike > 0) {
            music.setNbrOfLike(nbrLike - 1);
            musicRepository.saveAndFlush(music);
        }
    }

    private void increaseShare(Music music) {
        music.setNbrOfShare(music.getNbrOfShare() + 1);
        musicRepository.saveAndFlush(music);
    }

    private void decreaseShare(Music music) {
        int nbrShare = music.getNbrOfShare();

        if (nbrShare > 0) {
            music.setNbrOfShare(nbrShare - 1);
            musicRepository.saveAndFlush(music);
        }
    }

    private void increasePurchase(Music music) {
        music.setNbrOfPurchase(music.getNbrOfPurchase() + 1);
        musicRepository.saveAndFlush(music);
    }
}
