package com.keita.musicbay;

import com.keita.musicbay.controller.MusicController;
import com.keita.musicbay.model.*;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.service.CustomerService;
import com.keita.musicbay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
public class DbInit implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MusicRepository musicRepository;

    private void insertCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("bay").lastName("drip").picture("".getBytes()).dateOfBirth(LocalDate.of(1999, 12, 22))
                        .city("ATL").email("bayDrip@gmail.com").cellNumber("442-332-3421").userName("bayDrip").password("bayDrip123")
                        .biography("best rapper alive").build(),
                Customer.builder().firstName("brr").lastName("Big").picture("".getBytes()).dateOfBirth(LocalDate.of(1967, 12, 22))
                        .city("ATL").email("bigBrr@gmail.com").cellNumber("442-332-3421").userName("bigBrr").password("bigBrr123")
                        .biography("brr..bigBrr...").build()
        );


        customers.forEach(customer -> {
            try {
                customerService.createCustomer(customer, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void insertMusic(){
        List<Music> musics = Arrays.asList(
                MixTape.builder().title("culture1").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).build(),
                MixTape.builder().title("culture2").nbrOfLike(1).nbrOfPLay(1).nbrOfShare(1).nbrOfPurchase(0).build(),
                Track.builder().title("redRoom").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).build()
        );

        musics.get(0).getComments().add(Comment.builder().id(1L).content("nice!!").sendBy("bayDrip").nbrLike(0).build());

        musicRepository.saveAll(musics);
    }

    public void insertTransaction(){
        transactionService.createTransaction("bayDrip","culture1");
    }

    @Override
    public void run(String... args) throws Exception {
        insertCustomers();
        insertMusic();
        insertTransaction();
    }
}
