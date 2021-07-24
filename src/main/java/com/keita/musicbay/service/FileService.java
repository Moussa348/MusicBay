package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.File;
import com.keita.musicbay.repository.FileRepository;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Log
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public void play(String fileName, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("audio/mpeg");

        InputStream inputStream = new ByteArrayInputStream(fileRepository.findByFileName(fileName).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant find file with fileName: " + fileName)).getData());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    public java.io.File getFile(String fileName) throws Exception {
        File file = fileRepository.findByFileName(fileName).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find file : " + fileName));
        java.io.File fileToSend = new java.io.File("./" + fileName);

        FileUtils.writeByteArrayToFile(fileToSend, file.getData());

        log.info(file.toString());

        return fileToSend;
    }
    /*
    public ZipOutputStream zipFile(String fileName) throws Exception {
        File file = fileRepository.findByFileName(fileName).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find file : " + fileName));

        FileOutputStream fileOutputStream = new FileOutputStream(fileName.substring(0, fileName.indexOf(".")) + ".zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        java.io.File fileToZip = new java.io.File("./" + fileName);


        fileToZip.createNewFile();
            FileUtils.writeByteArrayToFile(fileToZip, file.getData());

            FileInputStream fileInputStream = new FileInputStream("./" + fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());

            zipOutputStream.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;

            while ((length = fileInputStream.read(bytes)) >= 0) {
                zipOutputStream.write(bytes, 0, length);
            }

            zipOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();


            return zipOutputStream;

    }

     */
}
