package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Liking> likings;

    @OneToMany(mappedBy = "customer")
    private List<Sharing> sharings;

    @OneToMany(mappedBy = "customer")
    private List<Purchasing> purchasings;

    public Customer() { }

    @Builder
    public Customer(String firstName, String lastName, String city, String email, String userName, String password, String biography, List<Order> orders,
                    List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings) {
        super(firstName, lastName,city, email, userName, password, biography);
        this.orders = orders;
        this.likings = likings;
        this.sharings = sharings;
        this.purchasings = purchasings;
    }
}
