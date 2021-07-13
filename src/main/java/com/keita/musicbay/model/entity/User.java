package com.keita.musicbay.model.entity;

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
    private String firstName,lastName,email,username,city,cellNumber,password,biography,roles;

    @Lob
    private byte[] picture;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationDate;
    private boolean active;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Subscriber> subscribers = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SubscribeTo> subscribeTos = new ArrayList<>();

    public User(){}

    public User(String firstName, String lastName, byte[] picture,LocalDate dateOfBirth,String cellNumber,String city,
                String email, String username, String password, String biography,String roles) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.email = email;
        this.cellNumber = cellNumber;
        this.username = username;
        this.password = password;
        this.biography = biography;
        this.users = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.subscribeTos = new ArrayList<>();
        this.registrationDate = LocalDateTime.now();
        this.picture = picture;
        this.roles = roles;
        this.active = true;
    }

}
