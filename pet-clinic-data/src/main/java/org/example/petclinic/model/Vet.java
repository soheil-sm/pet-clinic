package org.example.petclinic.model;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Vet extends Person {

    private Set<Specialty> especialties;

    public Set<Specialty> getEspecialties() {
        return especialties;
    }

    public void setEspecialties(Set<Specialty> especialties) {
        this.especialties = especialties;
    }
}
