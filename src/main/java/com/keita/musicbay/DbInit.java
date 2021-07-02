package com.keita.musicbay;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Profile("test")
@Component
@Order(1)
public class DbInit implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MusicRepository musicRepository;

    private void insertCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("bay").lastName("drip").picture("".getBytes()).dateOfBirth(LocalDate.of(1999, 12, 22))
                        .city("ATL").email("bayDrip@gmail.com").cellNumber("442-332-3421").userName("bayDrip").password("bayDrip123")
                        .biography("best rapper alive").build()
        );


        customers.forEach(customer -> {
            try {
                customerService.createCustomer(customer, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void insertMusic(){

    }

    @Override
    public void run(String... args) throws Exception {
        insertCustomers();

        insertMusic();
    }
}
