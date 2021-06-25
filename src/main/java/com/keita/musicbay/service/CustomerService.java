package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.Follower;
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

    public boolean saveCustomer(Customer customer) {
        if (!customerRepository.existsByEmail(customer.getEmail()) && !customerRepository.existsByUserName(customer.getUserName())) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public List<Follower> getListFollower(String username){
        Optional<Customer> customerOptional = customerRepository.findByUserName(username);
        return customerOptional.map(customer -> customer.getUsers().stream().map(Follower::new).collect(Collectors.toList())).orElse(null);
    }
}
