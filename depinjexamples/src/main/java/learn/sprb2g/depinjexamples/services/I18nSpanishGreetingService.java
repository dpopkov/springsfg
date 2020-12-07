package learn.sprb2g.depinjexamples.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"ES", "default"})
@Service("i18nService")
public class I18nSpanishGreetingService implements GreetingService {
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public String sayGreeting() {
        return "Hola Mundo - ES";
    }
}
