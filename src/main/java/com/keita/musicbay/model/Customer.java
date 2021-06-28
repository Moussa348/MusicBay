package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer")
    private List<Liking> likings;

    @OneToMany(mappedBy = "customer")
    private List<Sharing> sharings;

    @OneToMany(mappedBy = "customer")
    private List<Purchasing> purchasings;

    public Customer() { }

    @Builder
    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String cellNumber, String city, String email, String userName, String password, String biography, List<Transaction> transactions,
                    List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings) {
        super(firstName, lastName,dateOfBirth,cellNumber,city, email, userName, password, biography);
        //this.transactions = transactions;
        this.likings = likings;
        this.sharings = sharings;
        this.purchasings = purchasings;
    }
}
