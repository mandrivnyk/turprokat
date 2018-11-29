package ua.com.turprokat.service;



import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.com.turprokat.domain.Customer;
import ua.com.turprokat.repos.CustomerRepo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Service
public class MailSender {

    @Autowired
    private CustomerRepo customerRepo;

    @Value("${spring.viza.files.folder}")
    private  String pathToFolder;

    @Value("${spring.viza.code}")
    private  String code;

    @Value("${spring.viza.emailTo}")
    private  String emailTo;

    @Value("${spring.viza.emailToCopyBcc}")
    private  String emailToCopyBcc;

    @Value("${spring.viza.host}")
    private  String host;

    @Value("${spring.viza.port}")
    private  int port;




    public void sendToAll(String code){

        Iterable<Customer> customers = customerRepo.findAll();
//        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());

//        System.out.println(timeStamp);
//
//        while (!timeStamp.equals("164955") ) {
//            System.out.println("waiting..."+timeStamp);
//            timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
//        }

        for(Customer c:customers) {
            send(c);
            String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp);
        }
    }

    @Async
    public void send(Customer customer){
        try {
            long threadId = Thread.currentThread().getId();
            System.out.println("thread #" + threadId );
            String birthday = new SimpleDateFormat("dd-MM-yyyy").format(customer.getBirthday());

            String userName = customer.getEmail();
            String password = customer.getPassword();
            String subject = code;
            String text =   "•\tІм'я та прізвище заявника: "+customer.getName()+" "+customer.getSurname()+"\n"+
                            "•\tДата народження заявника: "+birthday+"\n"+
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
