package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> findAll();
}
