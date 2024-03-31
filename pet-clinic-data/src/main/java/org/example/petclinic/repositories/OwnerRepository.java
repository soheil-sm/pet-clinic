package org.example.petclinic.repositories;

import org.example.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

        Owner findByLastName(String lastName);
}
