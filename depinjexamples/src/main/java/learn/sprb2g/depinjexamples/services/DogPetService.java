package learn.sprb2g.depinjexamples.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dog"})
public class DogPetService implements PetService {
    @Override
    public String getPetType() {
        return "Dogs are the best";
    }
}
