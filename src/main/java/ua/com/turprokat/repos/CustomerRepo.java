package ua.com.turprokat.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.turprokat.domain.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
}

