package learn.sprb2g.restclient.services;

import learn.sprb2g.restclient.api.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers() {
        User[] users = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", User[].class);
        return users != null ? Arrays.asList(users) : List.of();
    }

    @Override
    public User getUser(Long id) {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/" + id, User.class);
    }
}
