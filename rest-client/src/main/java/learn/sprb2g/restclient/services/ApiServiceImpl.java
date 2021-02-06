package learn.sprb2g.restclient.services;

import learn.sprb2g.restclient.api.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<User> getUsers() {
        User[] users = restTemplate.getForObject(apiUrl, User[].class);
        return users != null ? Arrays.asList(users) : List.of();
    }

    @Override
    public User getUser(Long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .pathSegment(id.toString());
        return restTemplate.getForObject(uriBuilder.toUriString(), User.class);
    }
}
