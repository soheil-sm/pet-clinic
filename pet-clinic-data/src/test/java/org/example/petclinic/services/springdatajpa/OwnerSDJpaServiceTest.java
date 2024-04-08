package org.example.petclinic.services.springdatajpa;

import jakarta.persistence.Id;
import org.example.petclinic.model.Owner;
import org.example.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "smith";
    public static final long ID = 1L;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    @Mock
    OwnerRepository ownerRepository;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        assertEquals(LAST_NAME, ownerSDJpaService.findByLastName(LAST_NAME).getLastName());
//        This static method makes sure the findByLastName method has been called and called once
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> ownersSet = new HashSet<>();
        ownersSet.add(Owner.builder().id(1L).build());
        ownersSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(ownersSet);
        Set<Owner> owners = ownerSDJpaService.findAll();
        assertNotNull(owners);
        assertEquals(2, owners.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner = ownerSDJpaService.findById(ID);
        assertNotNull(owner);
        verify(ownerRepository).findById(any());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerSDJpaService.findById(ID);
        assertNull(owner);
    }

        @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        assertNotNull(ownerSDJpaService.save(returnOwner));
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(ID);
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}