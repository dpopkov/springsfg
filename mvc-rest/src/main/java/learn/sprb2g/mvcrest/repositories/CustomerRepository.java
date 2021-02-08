package learn.sprb2g.mvcrest.repositories;

import learn.sprb2g.mvcrest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
