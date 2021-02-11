package learn.sprb2g.mvcrest.repositories;

import learn.sprb2g.mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
