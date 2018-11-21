package ua.com.turprokat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepo customerRepo;


    public void addCustomer(Customer customer){
        customerRepo.save(customer);
    }

    public Iterable<Customer> findAll(){
            return customerRepo.findAll();
    }

}
