package com.keita.musicbay;

import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.model.entity.*;
import com.keita.musicbay.model.enums.ConversationType;
import com.keita.musicbay.model.enums.PriceType;
import com.keita.musicbay.repository.ConversationRepository;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.service.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
@Log
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConversationRepository conversationRepository;


    private void insertCustomers() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./docs/noUser.jpg");
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("bay").lastName("drip").password("bayDrip123").picture("".getBytes()).dateOfBirth(LocalDate.of(1999, 12, 22))
                        .city("ATL").picture(fileInputStream.readAllBytes()).email("bayDrip@gmail.com").cellNumber("442-332-3421").username("bayDrip").password("bayDrip123")
                        .biography("best rapper alive").roles("USER").build(),
                Customer.builder().firstName("brr").lastName("Big").picture(fileInputStream.readAllBytes()).password("bigBrr123").picture("".getBytes()).dateOfBirth(LocalDate.of(1967, 12, 22))
                        .city("ATL").email("bigBrr@gmail.com").cellNumber("442-332-3421").username("bigBrr").password("bigBrr123")
                        .biography("brr..bigBrr...").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("bombay").password("bombay123")
                        .biography("I love alcool and weed").roles("USER ADMIN").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("cash").password("cash123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("fabo").password("fabo123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("ricchi").password("ricchi123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("splash").password("splash123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("water").password("water123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("iceBurr").password("iceBurr123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("sasuske").password("sasuske123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("gintoki").password("gintoki123")
                        .biography("I love alcool and weed").roles("USER").build(),
                Customer.builder().firstName("salehe").lastName("jojo").picture(fileInputStream.readAllBytes()).password("bombay123").picture("".getBytes()).dateOfBirth(LocalDate.of(1998, 12, 22))
                        .city("MTL").email("developpeurspring@gmail.com").cellNumber("514-987-3221").username("massou").password("massou123")
                        .biography("I love alcool and weed").roles("USER").build()
        );

        log.info(customers.get(0).getPassword().toUpperCase());

        customerRepository.saveAll(customers);
    }

    private void insertTransaction(){
        transactionService.createTransaction("bombay","culture1", PriceType.BASIC);
    }

    private void insertConversation(){
        List<ConversationDTO> conversationDTOS = Arrays.asList(
                ConversationDTO.builder().createdBy("brr").name("glowGang").conversationType(ConversationType.GROUP).usernames(Arrays.asList("bigBrr","bombay")).build(),
                ConversationDTO.builder().createdBy("brr").name("glowGang").conversationType(ConversationType.GROUP).usernames(Arrays.asList("bayDrip","bombay")).build()
        );

        conversationDTOS.forEach(conversationDTO -> conversationService.createConversation(conversationDTO));
    }

    private void sendMessagesInConversations(){
        List<SentMessage> sentMessages = Arrays.asList(
                new SentMessage(Message.builder().content("allo").sendBy("brrr").conversation(conversationRepository.getById(1L)).build()),
                new SentMessage(Message.builder().content("allo").sendBy("bayDrip").conversation(conversationRepository.getById(2L)).build())
        );

        sentMessages.forEach(sentMessage -> conversationService.sendMessageInConversation(sentMessage));
    }

    private void insertComment(){
        List<PostedComment> postedComments = Arrays.asList(
            new PostedComment("nice instru you should get more views","bayDrip",0),
            new PostedComment("this shit firee cant liee","bigBrr",0),
            new PostedComment("broo you should drop some more","bayDrip",0)
        );

        postedComments.get(0).setId(1L);
        postedComments.get(1).setId(2L);
        postedComments.get(2).setId(3L);

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
        //insertMusic();
        insertTransaction();
        insertConversation();
        sendMessagesInConversations();
        insertComment();
        subscribe();
    }
}
