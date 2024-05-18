package org.example.petclinic.controllers;

import org.example.petclinic.model.Owner;
import org.example.petclinic.model.Pet;
import org.example.petclinic.model.PetType;
import org.example.petclinic.services.OwnerService;
import org.example.petclinic.services.PetService;
import org.example.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
class PetControllerTest {
    @Mock
    PetTypeService petTypeService;
    @Mock
    OwnerService ownerService;
    @InjectMocks
    PetController petController;
    @Mock
    PetService petService;
    MockMvc mockMvc;
    Set<PetType> petTypes;
    Owner owner;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).build());
        petTypes.add(PetType.builder().id(2L).build());
        petTypes.add(PetType.builder().id(3L).build());

        owner = Owner.builder().id(1L).pets(new HashSet<>()).build();
    }

    @Test
    void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.save(any())).thenReturn(owner);
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.save(any())).thenReturn(owner);
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/2/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(any());
    }

    @Test
    void populatePetType() {
//        todo Impl
    }

    @Test
    void findOwner() {
//        todo Impl
    }

    @Test
    void initOwnerBinder() {
//        todo Impl
    }
}