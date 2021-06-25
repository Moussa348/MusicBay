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
    private List<LikedMusicArticle> likedMusicArticles;

    @OneToMany(mappedBy = "customer")
    private List<SharedMusicArticle> sharedMusicArticles;

    @OneToMany(mappedBy = "customer")
    private List<PurchasedMusicArticle> purchasedMusicArticles;

    public Customer() { }

    @Builder
    public Customer(String firstName, String lastName, String city, String email, String userName, String password, String biography, byte[] picutre, List<Order> orders,
                    List<LikedMusicArticle> likedMusicArticles, List<SharedMusicArticle> sharedMusicArticles, List<PurchasedMusicArticle> purchasedMusicArticles) {
        super(firstName, lastName,city, email, userName, password, biography,picutre);
        this.orders = orders;
        this.likedMusicArticles = likedMusicArticles;
        this.sharedMusicArticles = sharedMusicArticles;
        this.purchasedMusicArticles = purchasedMusicArticles;
    }
}
