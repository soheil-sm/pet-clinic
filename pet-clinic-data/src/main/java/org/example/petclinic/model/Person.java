package org.example.petclinic.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends BaseEntity {
    private String firsName;
    private String lastName;

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
