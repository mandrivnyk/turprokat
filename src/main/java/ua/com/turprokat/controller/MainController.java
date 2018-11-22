package ua.com.turprokat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.service.CustomerService;
import ua.com.turprokat.service.MailSender;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private CustomerService customerService;

//    @Autowired
//    private MailSender mailSender;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Customer> customers = customerService.findAll();
        model.put("customers", customers);
        return "main";
    }

//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

    @PostMapping("/addCustomer")
    public String addNewUser(
                @RequestParam String name,
                @RequestParam String surname,
                @RequestParam String passport,
                @RequestParam String phone,
                @RequestParam String birthday,
                @RequestParam String email,
                @RequestParam String files,
                Map<String, Object> model
    ) {
        Date dBirthday;
        try {
            dBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException e) {
            dBirthday = new Date(1970,1,1);
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setBirthday(dBirthday);
        customer.setPassport(passport);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setEnable(true);
        customer.setDate(new Date());
        customerService.addCustomer(customer);
       // mailSender.send(email, "test", "test");

        Iterable<Customer> customers = customerService.findAll();
        model.put("customers", customers);
        return "main";
    }

//    @GetMapping(path = "/")
//    public @ResponseBody Iterable<Customer> getAllUsers(){
//        return customerRepo.findAll();
//    }

}
