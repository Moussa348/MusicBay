package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender  javaMailSender;

    public void sendConfirmationEmail(Customer customer, TransactionDTO transactionDTO) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Confirmation");
        mimeMessageHelper.setText(transactionDTO.toString() +
                "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }


    public void sendCancellation(Customer customer, TransactionDTO transactionDTO) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(customer.getEmail());
        mimeMessageHelper.setSubject("Transaction Cancelled");
        mimeMessageHelper
                .setText("Your transaction " +
                transactionDTO.getUuid() +
                "has been cancelled, because the payment can't be made." +
                        "\nMusicBay Inc.");

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
