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

        mimeMessageHelper.setText("TRANSACTION CANCELLED",true);
        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Cancelled");
        mimeMessageHelper
                .setText("Your transaction " +
                uuid +
                "has been cancelled, because the payment can't be made." +
                        "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

}
