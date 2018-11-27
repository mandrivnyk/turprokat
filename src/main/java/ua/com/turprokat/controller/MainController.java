package ua.com.turprokat.controller;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.service.CustomerService;
import ua.com.turprokat.service.MailSender;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MailSender mailSender;



    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Customer> customers = customerService.findAll();
        model.put("customers", customers);
        return "main";
    }


    @PostMapping("/addCustomer")
    public String addNewUser(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String passport,
            @RequestParam String phone,
            @RequestParam("birthday") @DateTimeFormat(pattern="yyyy-MM-dd") Date birthday,
            @RequestParam String email,
            @RequestParam String password,
//            @RequestParam String files,
            Map<String, Object> model
    ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setBirthday(birthday);
        customer.setPassport(passport);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setEnable(true);
        customer.setDate(new Date());
        customer.setPassword(password);
        customerService.addCustomer(customer);

        Iterable<Customer> customers = customerService.findAll();
        model.put("customers", customers);
        return "main";
    }

    @PostMapping("/sendAll")
    public String sendAll(Map<String, Object> model){
        Iterable<Customer> customers = customerService.findAll();
        mailSender.sendToAll();
        model.put("customers", customers);
        return "main";
    }

    @GetMapping("/showCode")
    public String showCode(Map<String, Object> model){
        Elements htmlWithCode = null;
        try {
            HashMap<String, String> cookie = new HashMap();
            Document res = Jsoup
                    .connect("https://www.mzv.cz/lvov/uk/x2004_02_03/x2016_05_18/x2017_11_24_1.html")
                    .get();
            String script = res.select("script").toString();
            int iStartCookie = script.indexOf("document.cookie=\"")+18;
            int iEndCookie = script.substring(iStartCookie).indexOf("\"");

            String sCookie = script.substring(iStartCookie-1, (iStartCookie + iEndCookie));
            String[] aCookies = sCookie.split("; ");

            for(String keyVal:aCookies)
            {
                String[] parts = keyVal.split("=",2);
                cookie.put(parts[0],parts[1]);
            }

            Document doc = Jsoup
                    .connect("https://www.mzv.cz/lvov/uk/x2004_02_03/x2016_05_18/x2017_11_24_1.html")
                    .cookies(cookie)
                    .get();
            htmlWithCode = doc.select("#content .article_body ol li:last-child ul li:last-child");
            if (htmlWithCode.size() == 0){
                htmlWithCode = doc.select("body");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.put("code", htmlWithCode);
        return "showcode";
    }


}
