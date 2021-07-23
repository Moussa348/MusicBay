package com.keita.musicbay.controller;

import com.keita.musicbay.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/play/{fileName}")
    public void play (@PathVariable String fileName, HttpServletResponse httpServletResponse) throws Exception{
        fileService.play(fileName,httpServletResponse);
    }
}
