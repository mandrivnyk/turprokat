package ua.com.turprokat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class AppRunner implements CommandLineRunner {
    @Autowired
    private CustomerRepo customerRepo;

    private final MailSender mailSender;
    public AppRunner(MailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();
        Iterable<Customer> customers = customerRepo.findAll();
        for(Customer c:customers) {
            mailSender.send(c);
            String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp);

        }
    }
}
