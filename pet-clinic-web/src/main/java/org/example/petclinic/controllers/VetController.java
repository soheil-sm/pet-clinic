package org.example.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    @RequestMapping({"vets", "vets/", "vets/index", "vets/index.html"})
    public String listVets(){
        return "vets/index";
    }
}
