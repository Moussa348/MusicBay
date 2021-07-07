package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean createCustomer(Registration registration) {
        if (!customerRepository.existsByEmail(registration.getEmail()) && !customerRepository.existsByUserName(registration.getUsername())) {
            //TODO : add default profile picture
            customerRepository.save(new Customer(registration));
            return true;
        }
        return false;
    }

    public Profile updateCustomer(Registration registration) throws Exception {
        Customer customer = customerRepository.findById(registration.getUuid()).get();


        return  customer.getUserName().equals(registration.getUsername()) ||
                !customerRepository.existsByUserName(registration.getUsername()) ?
                new Profile(customerRepository.save(new Customer(registration, customer))):null;
    }

    //TODO : add individual method to save picture

    public Profile getProfile(String username) {
        Customer customer = customerRepository.findByUserName(username).get();
        return new Profile(customer);
    }

    public void getPicture(String username, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(customerRepository.findByUserName(username).get().getPicture());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
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
