package org.example.petclinic.bootstrap;

import org.example.petclinic.model.Owner;
import org.example.petclinic.model.PetType;
import org.example.petclinic.model.Vet;
import org.example.petclinic.services.OwnerService;
import org.example.petclinic.services.PetTypeService;
import org.example.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        petTypeService.save(cat);

        Owner owner = new Owner();
        owner.setFirsName("Michel");
        owner.setLastName("Watson");
        ownerService.save(owner);

        Owner owner2 = new Owner();
        owner2.setFirsName("Fiona");
        owner2.setLastName("Glennane");
        ownerService.save(owner2);

        System.out.println("Loadded Owners...");

        Vet vet = new Vet();
        vet.setFirsName("Sam");
        vet.setLastName("Axe");
        vetService.save(vet);

        Vet vet2 = new Vet();
        vet2.setFirsName("Jessie");
        vet2.setLastName("Porter");
        vetService.save(vet2);

        System.out.println("Loadded Vets...");
    }
}
