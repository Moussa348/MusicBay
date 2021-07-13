package com.keita.musicbay;

import com.keita.musicbay.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.keita.musicbay.repository")
public class MusicBayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicBayApplication.class, args);
    }

}
