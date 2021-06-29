package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean createCustomer(Customer customer) {
        if (!customerRepository.existsByEmail(customer.getEmail()) && !customerRepository.existsByUserName(customer.getUserName())) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public Profile getProfile(String username) {
        Customer customer = customerRepository.findByUserName(username).get();
        return new Profile(customer);
    }

    public List<LikedMusic> getListLikedMusic(String username) {
        return customerRepository.findByUserName(username).get().getLikings().stream().map(LikedMusic::new).collect(Collectors.toList());
    }

    public List<SharedMusic> getListSharedMusic(String username) {
        return customerRepository.findByUserName(username).get().getSharings().stream().map(SharedMusic::new).collect(Collectors.toList());
    }

    public List<PurchasedMusic> getListPurchasedMusic(String username) {
        return customerRepository.findByUserName(username).get().getPurchasings().stream().map(PurchasedMusic::new).collect(Collectors.toList());
    }

    public List<Profile> getListSubscriber(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(username);
        List<Customer> customers = customerOptional.get().getSubscribers().stream().map(subscriber -> customerRepository.findByUserName(subscriber.getUsername()).get()).collect(Collectors.toList());

        return customers.stream().filter(Customer::isActive).map(Profile::new).collect(Collectors.toList());
    }

    public List<Profile> getListSubscribeTo(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(username);
        List<Customer> customers = customerOptional.get().getSubscribeTos().stream().map(subscribeTo -> customerRepository.findByUserName(subscribeTo.getUsername()).get()).collect(Collectors.toList());

        return customers.stream().filter(Customer::isActive).map(Profile::new).collect(Collectors.toList());
    }

}
