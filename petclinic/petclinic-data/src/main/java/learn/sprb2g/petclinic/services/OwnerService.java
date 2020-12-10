package learn.sprb2g.petclinic.services;

import learn.sprb2g.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
