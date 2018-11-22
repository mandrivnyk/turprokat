package ua.com.turprokat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String surname;
    private String passport;
    private String phone;
    private String email;
    private String files;

    @Type(type = "date")
    private Date birthday;

    @Type(type = "date")
    private Date date;

    private Boolean enable;


}
