package org.example.petclinic.services;

import org.example.petclinic.model.Owner;
import org.example.petclinic.model.Pet;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);

    Owner updatePet(Owner owner, Pet pet);
}
