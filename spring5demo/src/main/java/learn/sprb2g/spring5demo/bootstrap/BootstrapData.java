package learn.sprb2g.spring5demo.bootstrap;

import learn.sprb2g.spring5demo.model.Author;
import learn.sprb2g.spring5demo.model.Book;
import learn.sprb2g.spring5demo.repositories.AuthorRepository;
import learn.sprb2g.spring5demo.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BootstrapData(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {

        Author horstmann = new Author("Cay", "Horstmann");
        Author bloch = new Author("Joshua", "Bloch");
        Author goetz = new Author("Brian", "Goetz");
        Author tim = new Author("Tim", "Peierls");
        Author bowbeer = new Author("Joseph", "Bowbeer");
        Author holmes = new Author("David", "Holmes");
        Author lea = new Author("Doug", "Lea");
        authorRepository.save(horstmann);
        authorRepository.save(bloch);
        authorRepository.save(goetz);
        authorRepository.save(tim);
        authorRepository.save(bowbeer);
        authorRepository.save(holmes);
        authorRepository.save(lea);

        Book core = new Book("Core Java", "1234556");
        Book effective = new Book("Effective Java", "3456789");
        Book concurrency = new Book("Java Concurrency in Practice", "5889191");

        core.getAuthors().add(horstmann);
        horstmann.getBooks().add(core);

        effective.getAuthors().add(bloch);
        bloch.getBooks().add(effective);

        concurrency.getAuthors().add(goetz);
        goetz.getBooks().add(concurrency);
        concurrency.getAuthors().add(tim);
        tim.getBooks().add(concurrency);
        concurrency.getAuthors().add(bloch);
        bloch.getBooks().add(concurrency);
        concurrency.getAuthors().add(bowbeer);
        bowbeer.getBooks().add(concurrency);
        concurrency.getAuthors().add(holmes);
        holmes.getBooks().add(concurrency);
        concurrency.getAuthors().add(lea);
        lea.getBooks().add(concurrency);

        bookRepository.save(core);
        bookRepository.save(effective);
        bookRepository.save(concurrency);

        System.out.println("Number of authors: " + authorRepository.count());
        System.out.println("Number of books: " + bookRepository.count());
    }
}
