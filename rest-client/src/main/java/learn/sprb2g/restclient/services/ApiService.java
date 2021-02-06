package learn.sprb2g.restclient.services;

import learn.sprb2g.restclient.api.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers();

    User getUser(Long id);
}
