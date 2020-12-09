package learn.sprb2g.jokes.services;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

@Service
public class ChuckJokeService implements JokeService {
    private final ChuckNorrisQuotes quotes = new ChuckNorrisQuotes();

    @Override
    public String getJoke() {
        return quotes.getRandomQuote();
    }
}
