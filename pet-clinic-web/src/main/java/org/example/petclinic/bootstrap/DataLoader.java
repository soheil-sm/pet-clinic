package org.example.petclinic.bootstrap;

import org.example.petclinic.model.Owner;
import org.example.petclinic.model.Vet;
import org.example.petclinic.services.OwnerService;
import org.example.petclinic.services.VetService;
import org.example.petclinic.services.map.OwnerServiceMap;
import org.example.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private OwnerService ownerService;
    private VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirsName("Michel");
        owner.setLastName("Watson");
        ownerService.save(owner);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirsName("Fiona");
        owner2.setLastName("Glennane");
        ownerService.save(owner2);

        System.out.println("Loadded Owners...");

        Vet vet = new Vet();
        vet.setId(1L);
        vet.setFirsName("Sam");
        vet.setLastName("Axe");
        vetService.save(vet);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirsName("Jessie");
        vet2.setLastName("Porter");
        vetService.save(vet2);

        System.out.println("Loadded Vets...");
    }
}
