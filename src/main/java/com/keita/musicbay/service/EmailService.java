package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.dto.TransactionDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

@Service
@Log
public class EmailService {

    @Autowired
    private JavaMailSender  javaMailSender;

    @Autowired
    private FileService fileService;

    public void sendConfirmationEmail(Customer customer, TransactionDTO transactionDTO) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Confirmation");
        mimeMessageHelper.setText(transactionDTO.toString() + "We will send the items shortly" +
                "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }


    public void sendCancellationEmail(Customer customer, UUID uuid) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setText("ORDER CANCELATION",true);
        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Cancelled");
        mimeMessageHelper
                .setText("Your transaction " +
                uuid +
                "has been cancelled, because the payment can't be made." +
                        "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public void sendMusicArticles(Customer customer,List<MusicArticle> musicArticles) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("MUSIC ARTICLES");
        helper.setTo(customer.getEmail());
        helper.setText("HERE IS YOUR MUSIC ARTICLES", true);

        /*
            TODO:
                *based on priceType
                    -send mp3 file for basic
                    -send mp4 + wav for exclusive
         */

        musicArticles.forEach(musicArticle -> {
            try {
                helper.addAttachment(musicArticle.getTitle(),fileService.getFile(musicArticle.getTitle() + ".mp3"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        log.info("SENDING ARTICLES");
        javaMailSender.send(helper.getMimeMessage());

    }

   // private List<ZipOutputStream>
}
