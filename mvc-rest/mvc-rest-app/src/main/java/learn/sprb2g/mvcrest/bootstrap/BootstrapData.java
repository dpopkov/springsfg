package learn.sprb2g.mvcrest.bootstrap;

import learn.sprb2g.mvcrest.domain.Category;
import learn.sprb2g.mvcrest.domain.Customer;
import learn.sprb2g.mvcrest.domain.Vendor;
import learn.sprb2g.mvcrest.repositories.CategoryRepository;
import learn.sprb2g.mvcrest.repositories.CustomerRepository;
import learn.sprb2g.mvcrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootstrapData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public BootstrapData(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Loading data...");
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category("Fruits");
        Category dried = new Category("Dried");
        Category fresh = new Category("Fresh");
        Category exotic = new Category("Exotic");
        Category nuts = new Category("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Categories loaded. Count = {}.", categoryRepository.count());
    }

    private void loadCustomers() {
        Customer bob = new Customer();
        bob.setFirstname("Bob");
        bob.setLastname("Brown");
        Customer alice = new Customer();
        alice.setFirstname("Alice");
        alice.setLastname("White");

        customerRepository.save(bob);
        customerRepository.save(alice);

        log.info("Customers loaded. Count = {}.", customerRepository.count());
    }

    private void loadVendors() {
        Vendor tastyFruits = new Vendor("Western Tasty Fruits Ltd.");
        Vendor exoticFruits = new Vendor("Exotic Fruits Company");
        Vendor homeFruits = new Vendor("Home Fruits");

        vendorRepository.save(tastyFruits);
        vendorRepository.save(exoticFruits);
        vendorRepository.save(homeFruits);

        log.info("Vendors loaded. Count = {}.", vendorRepository.count());
    }
}
