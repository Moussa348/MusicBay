package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.File;
import com.keita.musicbay.model.entity.WavFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class FileRepositoryTest {


    @Autowired
    FileRepository fileRepository;

    @BeforeEach
    void insert(){
        List<File> files = Arrays.asList(
                WavFile.builder().fileName("taa.wav").build()
        );

        fileRepository.saveAll(files);
    }

    @Test
    void existsByFileName(){
        //ARRANGE
        String fileName ="taa.wav";

        //ACT
        boolean fileExist = fileRepository.existsByFileName(fileName);

        //ASSERT
        assertTrue(fileExist);

    }

    @Test
    void findByFileName(){
        //ARRANGE
        String fileName ="taa.wav";

        //ACT
        boolean fileExist = fileRepository.findByFileName(fileName).isPresent();

        //ASSERT
        assertTrue(fileExist);

    }
}
