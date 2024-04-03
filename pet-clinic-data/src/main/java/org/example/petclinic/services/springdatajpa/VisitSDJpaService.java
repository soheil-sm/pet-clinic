package org.example.petclinic.services.springdatajpa;

import org.example.petclinic.model.Visit;
import org.example.petclinic.repositories.VisitRepsitory;
import org.example.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSDJpaService implements VisitService {

    private final VisitRepsitory visitRepsitory;

    public VisitSDJpaService(VisitRepsitory visitRepsitory) {
        this.visitRepsitory = visitRepsitory;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepsitory.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long aLong) {
        return visitRepsitory.findById(aLong).orElse(null);
    }

    @Override
    public Visit save(Visit object) {
        return visitRepsitory.save(object);
    }

    @Override
    public void delete(Visit object) {
        visitRepsitory.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        visitRepsitory.deleteById(aLong);
    }
}
