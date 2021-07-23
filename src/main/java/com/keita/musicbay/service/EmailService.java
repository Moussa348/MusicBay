package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
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
        mimeMessageHelper.setText(transactionDTO.toString() +
                "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }


    public void sendCancellationEmail(Customer customer, TransactionDTO transactionDTO) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setText("ORDER CANCELATION",true);
        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Cancelled");
        mimeMessageHelper
                .setText("Your transaction " +
                transactionDTO.getUuid() +
                "has been cancelled, because the payment can't be made." +
                        "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public void sendConfirmation(List<MusicArticle> musicArticles) throws Exception{
        List<ZipOutputStream> zipOutputStreams = new ArrayList<>();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);



        helper.setSubject("CONFIRMATION");
        helper.setText("HERE IS YOUR MUSIC ARTICLES", true);
        javaMailSender.send(helper.getMimeMessage());
    }

   // private List<ZipOutputStream>
}
