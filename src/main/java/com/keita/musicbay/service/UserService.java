package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.Profile;
import com.keita.musicbay.model.dto.ProfileToSubscribeTo;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Profile getProfile(String username) {
        return new Profile(getUserByUsername(username));
    }

    public void getPicture(String username, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(getUserByUsername(username).getPicture());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    public List<ProfileToSubscribeTo> getListProfileSearch(){
        return userRepository.findAll().stream().filter(User::isActive).map(ProfileToSubscribeTo::new).collect(Collectors.toList());
   }

    public byte[] setDefaultProfilePicture() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./docs/noUser.jpg");
        return fileInputStream.readAllBytes();
    }

    private User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Can't find Customer with username: " + username));
    }
}
