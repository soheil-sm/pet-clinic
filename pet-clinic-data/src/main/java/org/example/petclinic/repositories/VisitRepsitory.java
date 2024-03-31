package org.example.petclinic.repositories;

import org.example.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepsitory extends CrudRepository<Visit, Long> {
}
