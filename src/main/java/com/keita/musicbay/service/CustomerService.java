package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.User;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean createCustomer(Registration registration) throws Exception {
        if (!customerRepository.existsByEmail(registration.getEmail()) && !customerRepository.existsByUsername(registration.getUsername())) {
            //TODO : add default profile picture
            Customer customer = new Customer(registration, setDefaultProfilePicture());
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    private byte[] setDefaultProfilePicture() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("./docs/noUser.jpg");
        return fileInputStream.readAllBytes();
    }

    public Profile updateCustomer(Registration registration) throws Exception {
        Customer customer = customerRepository.findById(registration.getUuid()).get();


        return  customer.getUsername().equals(registration.getUsername()) ||
                !customerRepository.existsByUsername(registration.getUsername()) ?
                new Profile(customerRepository.save(new Customer(registration, customer))):null;
    }

    //TODO : add individual method to save picture

    public Profile getProfile(String username) {
        Customer customer = customerRepository.findByUsername(username).get();
        return new Profile(customer);
    }

    public void getPicture(String username, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(customerRepository.findByUsername(username).get().getPicture());

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    public List<LikedMusic> getListLikedMusic(String username) {
        return customerRepository.findByUsername(username).get().getLikings().stream().map(LikedMusic::new).collect(Collectors.toList());
    }

    public List<SharedMusic> getListSharedMusic(String username) {
        return customerRepository.findByUsername(username).get().getSharings().stream().map(SharedMusic::new).collect(Collectors.toList());
    }

    public List<PurchasedMusic> getListPurchasedMusic(String username) {
        return customerRepository.findByUsername(username).get().getPurchasings().stream().map(PurchasedMusic::new).collect(Collectors.toList());
    }

    public List<Profile> getListSubscriber(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        List<Customer> customers = customerOptional.get().getSubscribers().stream().map(subscriber -> customerRepository.findByUsername(subscriber.getUsername()).get()).collect(Collectors.toList());

        return customers.stream().filter(Customer::isActive).map(Profile::new).collect(Collectors.toList());
    }

    public List<Profile> getListSubscribeTo(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        List<Customer> customers = customerOptional.get().getSubscribeTos().stream().map(subscribeTo -> customerRepository.findByUsername(subscribeTo.getUsername()).get()).collect(Collectors.toList());

        return customers.stream().filter(Customer::isActive).map(Profile::new).collect(Collectors.toList());
    }

}
