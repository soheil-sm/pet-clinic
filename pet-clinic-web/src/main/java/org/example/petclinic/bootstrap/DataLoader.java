package org.example.petclinic.bootstrap;

import org.example.petclinic.model.*;
import org.example.petclinic.services.OwnerService;
import org.example.petclinic.services.PetTypeService;
import org.example.petclinic.services.SpecialtyService;
import org.example.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        Owner owner = new Owner();
        owner.setFirsName("Michel");
        owner.setLastName("Watson");
        owner.setCity("Miami");
        owner.setAddress("123 Brickerel");
        owner.setTelephone("123123123123");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner);
        mikesPet.setBirtDate(LocalDate.now());
        mikesPet.setName("Rosco");
        owner.getPets().add(mikesPet);

        ownerService.save(owner);

        Owner owner2 = new Owner();
        owner2.setFirsName("Fiona");
        owner2.setLastName("Glennane");
        owner.setCity("Miami");
        owner.setAddress("123 Brickerel");
        owner.setTelephone("123123123123");

        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedCatPetType);
        fionasPet.setBirtDate(LocalDate.now());
        fionasPet.setName("just cat");
        fionasPet.setOwner(owner2);
        owner2.getPets().add(fionasPet);

        ownerService.save(owner2);

        System.out.println("Loadded Owners...");

        Vet vet = new Vet();
        vet.setFirsName("Sam");
        vet.setLastName("Axe");
        vet.getSpecialties().add(savedRadiology);

        vetService.save(vet);

        Vet vet2 = new Vet();
        vet2.setFirsName("Jessie");
        vet2.setLastName("Porter");
        vet.getSpecialties().add(savedDentistry);

        vetService.save(vet2);

        System.out.println("Loadded Vets...");
    }
}
