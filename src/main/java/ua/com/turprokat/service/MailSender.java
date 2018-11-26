package ua.com.turprokat.service;



import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

import java.io.File;



@Service
public class MailSender {

    @Autowired
    private CustomerRepo customerRepo;

    private  String pathToFolder = "C:/viza/";

    private  String code = "CODE_HERE";

    private  String emailTo = "easier@ukr.net";
    private  String emailToCopyBcc = "shop@mandrivnyk.kiev.ua";
//  private  String emailTo = "zamkarta_lvov@mzv.cz;";

    private  String host = "smtp.gmail.com";
    private  int port = 587;




    public void sendToAll(){

        Iterable<Customer> customers = customerRepo.findAll();

        for(Customer c:customers) {
                send(c);
        }
    }


    private void send(Customer customer){
        try {

            String userName = customer.getEmail();
            String password = customer.getPassword();
            String subject = code;
            String text =   "•\tІм'я та прізвище заявника: "+customer.getName()+" "+customer.getSurname()+"\n"+
                            "•\tДата народження заявника: "+customer.getBirthday()+"\n"+
                            "•\tНомер закордонного паспорта заявника: "+customer.getPassport()+"\n"+
                            "•\tКонтактні дані заявника (телефон та e-mail): "+customer.getPhone()+"; "+customer.getEmail()+"\n";


            MultiPartEmail simpleEmail = new MultiPartEmail();
            simpleEmail.setHostName(host);
            simpleEmail.setSmtpPort(port);
            simpleEmail.setAuthenticator(new DefaultAuthenticator(userName, password));
            simpleEmail.setSSLOnConnect(true);
            simpleEmail.setFrom(userName);
            simpleEmail.setSubject(subject);
            simpleEmail.setMsg(text);

            simpleEmail.addTo(emailTo);
            simpleEmail.addBcc(emailToCopyBcc);

            File myFolder = new File(pathToFolder+userName);
            File[] files = myFolder.listFiles();
            for(File file: files) {
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(pathToFolder+userName+"/"+file.getName());
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("doc");
                attachment.setName(file.getName());
                simpleEmail.attach(attachment);
            }

            simpleEmail.send();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
