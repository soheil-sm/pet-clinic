package org.example.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.petclinic.model.Owner;
import org.example.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Slf4j
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedField(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping()
    public String processFindForm(Owner owner, BindingResult result, Model model) {
//        allow parameterless Get request for /owners to return all records
        if (owner.getLastName() == null)
            owner.setLastName(""); // empty string signifies broadest possible search

//        find owners by lastName
        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        log.debug("" + results.size());
        if (results.isEmpty()) {
//            no owners found
            result.rejectValue("lastName", "notFound", "notFound");
            return "owners/findOwners";
        } else if (results.size() == 1) {
//            one owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
//            multiple owner found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }
}
