package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.LikedMusicArticle;
import com.keita.musicbay.model.MusicArticle;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.Follower;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.MusicArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean saveCustomer(Customer customer) {
        if (!customerRepository.existsByEmail(customer.getEmail()) && !customerRepository.existsByUserName(customer.getUserName())) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public Follower follow(String username, String usernameToFollow) {
        Customer customer = customerRepository.findByUserName(username).get();
        Customer customerToFollow = customerRepository.findByUserName(usernameToFollow).get();
        customer.getUsers().add(customerToFollow);

        customerRepository.save(customer);

        return new Follower(customerToFollow);
    }

    public List<Follower> getListFollower(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(username);
        return customerOptional.map(customer -> customer.getUsers().stream().map(Follower::new).collect(Collectors.toList())).orElse(null);
    }

}
