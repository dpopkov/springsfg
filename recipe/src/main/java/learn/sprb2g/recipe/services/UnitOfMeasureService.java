package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAll();
}
