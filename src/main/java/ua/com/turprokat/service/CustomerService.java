package ua.com.turprokat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MailSender mailSender;

    public void addCustomer(Customer customer){
        customerRepo.save(customer);
        mailSender.send(customer.getEmail(), "test", "text1");
    }

    public Iterable<Customer> findAll(){
            return customerRepo.findAll();
    }

}
