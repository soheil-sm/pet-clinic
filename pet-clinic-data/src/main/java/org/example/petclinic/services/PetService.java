package org.example.petclinic.services;

import org.example.petclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(long id);
    Pet save(Pet pet);
    Set<Pet> findAll();
}
