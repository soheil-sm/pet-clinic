package org.example.petclinic.model;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Owner extends Person {

    private Set<Pet> pets;

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
