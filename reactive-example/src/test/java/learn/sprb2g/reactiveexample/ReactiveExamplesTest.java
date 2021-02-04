package learn.sprb2g.reactiveexample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ReactiveExamplesTest {

    private final Person michael = new Person("Michael", "Weston");
    private final Person fiona = new Person("Fiona", "Glenanne");
    private final Person sam = new Person("Sam", "Axe");
    private final Person jesse = new Person("Jesse", "Porter");

    @Test
    void monoTests() {
        Mono<Person> personMono = Mono.just(michael);
        Person person = personMono.block();
        log.info(person.sayName());
    }

    @Test
    void monoTransform() {
        Mono<Person> personMono = Mono.just(fiona);
        PersonCommand command = personMono.map(PersonCommand::new).block();
        log.info(command.sayName());
    }

    @Test
    void monoFilter() {
        Mono<Person> personMono = Mono.just(sam);
        Person samAxe = personMono
                .filter(person -> person.getFirstName().equalsIgnoreCase("foo"))
                .block();
        assertThrows(NullPointerException.class, () -> log.info(samAxe.sayName()));
    }

    @Test
    void fluxTest() {
        Flux<Person> people = Flux.just(michael, fiona, sam, jesse);
        people.subscribe(person -> log.info(person.sayName()));
    }

    @Test
    void fluxTestFilter() {
        Flux<Person> people = Flux.just(michael, fiona, sam, jesse);
        people.filter(person -> person.getFirstName().equals(fiona.getFirstName()))
                .subscribe(person -> log.info(person.sayName()));
    }

    @Test
    void fluxTestDelayNoOutput() {
        Flux<Person> people = Flux.just(michael, fiona, sam, jesse);
        people.delayElements(Duration.ofSeconds(1))
                .subscribe(person -> log.info(person.sayName()));
    }

    @Test
    void fluxTestDelay() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<Person> people = Flux.just(michael, fiona, sam, jesse);
        people.delayElements(Duration.ofSeconds(1))
                .doOnComplete(latch::countDown)
                .subscribe(person -> log.info(person.sayName()));
        latch.await();
    }

    @Test
    void fluxTestFilterDelay() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<Person> people = Flux.just(michael, fiona, sam, jesse);
        people.delayElements(Duration.ofSeconds(1))
                .filter(person -> person.getFirstName().contains("i"))
                .doOnComplete(latch::countDown)
                .subscribe(person -> log.info(person.sayName()));
        latch.await();
    }
}
