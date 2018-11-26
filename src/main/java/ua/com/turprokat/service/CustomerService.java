package ua.com.turprokat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

import java.io.File;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepo customerRepo;

    @Value("${spring.viza.files.folder}")
    private  String pathToFolder;

    public void addCustomer(Customer customer){
        customerRepo.save(customer);
        createCustomerFolder(customer);
    }

    private void createCustomerFolder(Customer customer){
        new File(pathToFolder+customer.getEmail()).mkdirs();
    }

    public Iterable<Customer> findAll(){
            return customerRepo.findAll();
    }



}
