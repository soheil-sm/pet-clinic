package org.example.petclinic.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.petclinic.model.Owner;
import org.example.petclinic.model.Pet;
import org.example.petclinic.model.Visit;
import org.example.petclinic.services.OwnerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class VisitController {
    public static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private final OwnerService ownerService;

    public VisitController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
     * we always have fresh data - Since we do not use the session scope, make sure that
     * Pet object always has an id (Even though id is not part of the form fields)
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long ownerId, @PathVariable Long petId,
                                  Model model) {
        Owner owner = ownerService.findById(ownerId);

        Pet pet = owner.getPet(petId);
        if(pet == null)
            log.debug("pet is empty");
        model.addAttribute("pet", pet);
        model.addAttribute("owner", owner);

        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
    // called
    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm() {
        return PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
    // called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@ModelAttribute Owner owner, @PathVariable Long petId, @Valid Visit visit,
                                      BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return PETS_CREATE_OR_UPDATE_VISIT_FORM;
        }

//        owner.addVisit(petId, visit);
        owner.getPets()
                .stream()
                .filter(pet -> pet.getId().equals(petId))
                .findFirst()
                .ifPresent(pet -> pet.getVisits().add(visit));

        Owner savedOwner = ownerService.save(owner);
        redirectAttributes.addFlashAttribute("message", "Your visit has been booked");
        return "redirect:/owners/" + savedOwner.getId();
    }

}
