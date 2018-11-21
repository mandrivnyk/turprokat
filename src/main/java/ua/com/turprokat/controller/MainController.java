package ua.com.turprokat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.service.CustomerService;
import ua.com.turprokat.service.MailSender;


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
    public String addNewUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email, Map<String, Object> model) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setEnable(true);
        customer.setDate(new java.util.Date());
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
