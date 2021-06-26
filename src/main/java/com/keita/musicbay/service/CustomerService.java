package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.Follower;
import com.keita.musicbay.model.dto.MusicDTO;
import com.keita.musicbay.model.dto.Profile;
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

    public Profile getProfile(String username){
        Customer customer = customerRepository.findByUserName(username).get();
        return new Profile(customer,customer.getLikedMusics(),customer.getSharedMusics(),customer.getPurchasedMusics());
    }

    public void follow(String username, String usernameToFollow) {
        Customer customer = customerRepository.findByUserName(username).get();
        Customer customerToFollow = customerRepository.findByUserName(usernameToFollow).get();

        customer.getUsers().add(customerToFollow);

        customerRepository.save(customer);
    }

    public List<MusicDTO> getListLikedMusic(String username){
        return customerRepository.findByUserName(username).get().getLikedMusics().stream().map(MusicDTO::new).collect(Collectors.toList());
    }

    public List<MusicDTO> getListSharedMusic(String username){
        return customerRepository.findByUserName(username).get().getSharedMusics().stream().map(MusicDTO::new).collect(Collectors.toList());
    }

    public List<MusicDTO> getListPurchasedMusic(String username){
        return customerRepository.findByUserName(username).get().getPurchasedMusics().stream().map(MusicDTO::new).collect(Collectors.toList());
    }

    public List<Follower> getListFollower(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(username);
        return customerOptional.map(customer -> customer.getUsers().stream().filter(User::isActive).map(Follower::new).collect(Collectors.toList())).orElse(null);
    }

}
