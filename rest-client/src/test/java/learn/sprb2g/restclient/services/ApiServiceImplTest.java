package learn.sprb2g.restclient.services;

import learn.sprb2g.restclient.api.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled("This is not a real test")
@SpringBootTest
class ApiServiceImplTest {

    @Autowired
    ApiService apiService;

    @Test
    void testGetUsers() {
        List<User> users = apiService.getUsers();
        users.forEach(System.out::println);
    }

    @Test
    void testGetUser() {
        User user = apiService.getUser(1L);
        System.out.println(user);
    }
}
