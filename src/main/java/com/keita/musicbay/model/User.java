package com.keita.musicbay.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String firstName,lastName,email,userName,city,password,biography;

    @Lob
    @Column(name ="picture",columnDefinition = "BLOB")
    private byte[] picture;
    private LocalDateTime registrationDate;
    private boolean active;

    @ManyToMany(mappedBy = "users")
    private List<Message> messages;

    @ManyToMany
    private List<User> users;

    public User(){}

    public User(String firstName, String lastName,String city, String email, String userName, String password, String biography,byte[] picture) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.biography = biography;
        this.picture = picture;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

}
