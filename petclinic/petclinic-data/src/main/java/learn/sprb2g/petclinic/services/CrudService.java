package learn.sprb2g.petclinic.services;

import learn.sprb2g.petclinic.model.BaseEntity;

import java.util.Set;

public interface CrudService<T extends BaseEntity, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T entity);

    void delete(T object);

    void deleteById(ID id);
}
