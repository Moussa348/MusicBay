package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    MusicRepository musicRepository;

    @InjectMocks
    MusicService musicService;

    @Test
    void getMusic(){
        //ARRANGE
        String title = "IceBurr";
        when(musicRepository.findByTitle(title)).thenReturn(Optional.of(Track.builder().build()));

        //ACT
        MusicDTO musicDTO = musicService.getMusic(title);

        //ASSERT
        assertNotNull(musicDTO);

    }

    @Test
    void getListMusic(){
        //ARRANGE
        List<Music> musics = Arrays.asList(
                Track.builder().build(),
                MixTape.builder().build(),
                MixTape.builder().build(),
                Track.builder().build()
        );

        when(musicRepository.findAll()).thenReturn(musics);
        //ACT
        List<MusicDTO> musicDTOS = musicService.getListMusic();

        //ASSERT
        assertEquals(4,musicDTOS.size());

    }
}
