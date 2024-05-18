package org.example.petclinic.controllers;


import org.example.petclinic.model.Owner;
import org.example.petclinic.model.Pet;
import org.example.petclinic.model.PetType;
import org.example.petclinic.services.OwnerService;
import org.example.petclinic.services.PetService;
import org.example.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetTypeService petTypeService;
    public final OwnerService ownerService;
    public final PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetType() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model,
                                      RedirectAttributes redirectAttributes) {
        if (StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }

        LocalDate currentDate = LocalDate.now();
        if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            result.rejectValue("birthDate", "typeMismatch.birthDate");
        }

        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }

        Owner savedOwner = ownerService.save(owner);
        redirectAttributes.addFlashAttribute("message", "New Pet has been Added");
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model,
                                    RedirectAttributes redirectAttributes) {

        String petName = pet.getName();

        // checking if the pet name already exist for the owner
        if (StringUtils.hasText(petName)) {
            Pet existingPet = owner.getPet(petName.toLowerCase(), false);
            if (existingPet != null && !Objects.equals(existingPet.getId(), pet.getId())) {
                result.rejectValue("name", "duplicate", "already exists");
            }
        }

        LocalDate currentDate = LocalDate.now();
        if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
            result.rejectValue("birthDate", "typeMismatch.birthDate");
        }

        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }

        owner.getPets().add(pet);
        Owner savedOwner = ownerService.save(owner);
        redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
        return "redirect:/owners/" + savedOwner.getId();
    }

}
