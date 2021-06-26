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
    private List<LikedMusic> likedMusics;

    @OneToMany(mappedBy = "customer")
    private List<SharedMusic> sharedMusics;

    @OneToMany(mappedBy = "customer")
    private List<PurchasedMusic> purchasedMusics;

    public Customer() { }

    @Builder
    public Customer(String firstName, String lastName, String city, String email, String userName, String password, String biography, List<Order> orders,
                    List<LikedMusic> likedMusics, List<SharedMusic> sharedMusics, List<PurchasedMusic> purchasedMusics) {
        super(firstName, lastName,city, email, userName, password, biography);
        this.orders = orders;
        this.likedMusics = likedMusics;
        this.sharedMusics = sharedMusics;
        this.purchasedMusics = purchasedMusics;
    }
}
