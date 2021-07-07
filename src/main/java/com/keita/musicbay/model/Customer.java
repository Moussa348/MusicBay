package com.keita.musicbay.model;

import com.keita.musicbay.model.dto.Profile;
import com.keita.musicbay.model.dto.Registration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Liking> likings;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sharing> sharings;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Purchasing> purchasings;

    public Customer() { }

    @Builder
    public Customer(String firstName, String lastName,byte[] picture, LocalDate dateOfBirth, String cellNumber, String city, String email, String userName, String password, String biography, List<Transaction> transactions,
                    List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings) {
        super(firstName, lastName,picture,dateOfBirth,cellNumber,city, email, userName, password, biography);
        this.transactions = transactions;
        this.likings = likings;
        this.sharings = sharings;
        this.purchasings = purchasings;
    }

    public Customer(Registration registration){
        super("", "",null,null,"","", registration.getEmail(), registration.getUsername(), registration.getPassword(), "");
    }

    public Customer(Registration registration, Customer customer, byte[] picture){
        super("", "", picture, registration.getDate(),"", registration.getCity(), customer.getEmail(), customer.getUserName(), registration.getPassword(), registration.getBiography());
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
