package com.keita.musicbay;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.model.enums.ConversationType;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MonitoringService monitoringService;

    private void insertCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("bay").lastName("drip").picture("".getBytes()).dateOfBirth(LocalDate.of(1999, 12, 22))
                        .city("ATL").email("bayDrip@gmail.com").cellNumber("442-332-3421").userName("bayDrip").password("bayDrip123")
                        .biography("best rapper alive").build(),
                Customer.builder().firstName("brr").lastName("Big").picture("".getBytes()).dateOfBirth(LocalDate.of(1967, 12, 22))
                        .city("ATL").email("bigBrr@gmail.com").cellNumber("442-332-3421").userName("bigBrr").password("bigBrr123")
                        .biography("brr..bigBrr...").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("bombay@gmail.com").cellNumber("514-987-3221").userName("bombay").password("bombay123")
                        .biography("I love alcool and weed").build()
        );


        customers.forEach(customer -> {
            try {
                customerService.createCustomer(customer, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void insertMusic(){
        List<Music> musics = Arrays.asList(
                MixTape.builder().timeLength("34:00").title("culture1").description("").tags("Dark Hip-Hop rap").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).price(24.0f).build(),
                MixTape.builder().timeLength("55:00").title("").title("culture2").description("").tags("Hip-Hop Rap").nbrOfLike(1).nbrOfPLay(1).nbrOfShare(1).nbrOfPurchase(0).price(50.0f).build(),
                Track.builder().timeLength("2:00").title("redRoom").tags("RAP").description("").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).price(30.0f).bpm(100f).build(),
                Track.builder().timeLength("3:00").title("Ragnarok").tags("RAP").description("").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).price(30.0f).bpm(100f).build(),
                Track.builder().timeLength("1:50").title("Winter").tags("RAP").description("").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).price(30.0f).bpm(100f).build(),
                Track.builder().timeLength("2:30").title("Mob").tags("RAP").description("").nbrOfLike(0).nbrOfPLay(0).nbrOfShare(0).nbrOfPurchase(0).price(30.0f).bpm(100f).build()
        );

        musics.get(0).getComments().add(Comment.builder().id(1L).content("nice!!").sendBy("bayDrip").nbrLike(0).build());

        musicRepository.saveAll(musics);
    }

    private void insertTransaction(){
        transactionService.createTransaction("bayDrip","culture1");
    }

    private void insertConversation(){
        List<ConversationDTO> conversationDTOS = Arrays.asList(
                new ConversationDTO("migos", ConversationType.GROUP)
        );
        conversationDTOS.get(0).getUsernames().add("bigBrr");
        conversationDTOS.get(0).getUsernames().add("bayDrip");

        conversationDTOS.forEach(conversationDTO -> conversationService.createConversation(conversationDTO));
    }

    private void insertComment(){
        List<PostedComment> postedComments = Arrays.asList(
            new PostedComment("nice instru you should get more views","bayDrip",0),
            new PostedComment("this shit firee cant liee","bigBrr",0),
            new PostedComment("broo you should drop some more","bayDrip",0)
        );

        postedComments.forEach(postedComment -> commentService.postComment(postedComment,"culture1"));
    }

    private void subscribe(){
        monitoringService.subscribe("bayDrip","bombay");
        monitoringService.subscribe("bigBrr","bombay");
        monitoringService.subscribe("bombay","bigBrr");
    }

    @Override
    public void run(String... args) throws Exception {
        insertCustomers();
        insertMusic();
        insertTransaction();
        insertConversation();
        insertComment();
        subscribe();
    }
}
