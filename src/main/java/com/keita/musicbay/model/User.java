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
    private String firstName,lastName,email,userName,city,cellNumber,password,biography;

    @Lob
    @Column(name ="picture",columnDefinition = "BLOB")
    private byte[] picture;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationDate;
    private boolean active;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "user")
    private List<Subscriber> subscribers;

    @OneToMany(mappedBy = "user")
    private List<SubscribeTo> subscribeTos;

    public User(){}

    public User(String firstName, String lastName,LocalDate dateOfBirth,String cellNumber,String city, String email, String userName, String password, String biography) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.email = email;
        this.cellNumber = cellNumber;
        this.userName = userName;
        this.password = password;
        this.biography = biography;
        this.users = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.subscribeTos = new ArrayList<>();
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

}
