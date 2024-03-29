package com.keita.musicbay.model.entity;

import com.keita.musicbay.model.dto.Registration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Liking> likings = new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sharing> sharings = new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Purchasing> purchasings = new ArrayList<>();

    public Customer() { }

    @Builder
    public Customer(UUID uuid, String firstName, String lastName, byte[] picture, LocalDate dateOfBirth, String cellNumber, String city, String email, String username, String password, String biography, List<Transaction> transactions,
                    List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings,String roles) {
        super(firstName, lastName,picture,dateOfBirth,cellNumber,city, email, username, password, biography,roles);
        this.transactions = transactions;
        this.likings = likings;
        this.sharings = sharings;
        this.purchasings = purchasings;
    }

    public Customer(Registration registration){
        super("", "",null,null,"","", registration.getEmail(), registration.getUsername(), registration.getPassword(), "","USER");
    }

    public Customer(Registration registration, byte[] picture){
        super("", "",picture,null,"","", registration.getEmail(), registration.getUsername(), registration.getPassword(), "","USER");
    }

    public Customer(Registration registration, Customer customer){
        super("", "", customer.getPicture(), registration.getDate(),"", registration.getCity(), customer.getEmail(), registration.getUsername(), registration.getPassword(), registration.getBiography(),"USER");
        super.setUuid(customer.getUuid());
        super.setSubscribers(customer.getSubscribers());
        super.setSubscribeTos(customer.getSubscribeTos());
        super.setActive(customer.isActive());
        super.setRegistrationDate(customer.getRegistrationDate());
        super.setUsers(customer.getUsers());
        this.transactions = customer.getTransactions();
        this.likings = customer.getLikings();
        this.sharings = customer.getSharings();
        this.purchasings = customer.getPurchasings();
    }

}
