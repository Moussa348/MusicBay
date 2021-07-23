package com.keita.musicbay.service;

import com.keita.musicbay.model.entity.File;
import com.keita.musicbay.model.entity.Mp3File;
import com.keita.musicbay.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Optional;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    FileRepository fileRepository;

    @InjectMocks
    FileService fileService;

    @Test
    void play() throws Exception{
        //ARRANGE
        String fileName = "Hope.mp3";

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        mockHttpServletResponse.setContentType("audio/mpeg");

        when(fileRepository.findByFileName(fileName)).thenReturn(Optional.of(Mp3File.builder().data("".getBytes()).build()));

        //ACT
        fileService.play(fileName,mockHttpServletResponse);
    }

    @Test
    void zipFile() throws Exception{
        //ARRANGE
        File file = Mp3File.builder().fileName("Hope.txt").data("sadasd".getBytes()).build();
        when(fileRepository.findByFileName(file.getFileName())).thenReturn(Optional.of(file));


        //ACT
        ZipOutputStream zipFile = fileService.zipFile(file.getFileName());

        //ASSERT
        assertNotNull(zipFile);
    }
}
