package ua.donetc.springmvc.config.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.donetc.springmvc.config.dao.PersonDAO;
import ua.donetc.springmvc.config.models.Person;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());
        return "/admin-page";
    }


    @PatchMapping("/add")
    public String createAdmin(@ModelAttribute("person") Person person ){
        System.out.println(person.getId());
        return "redirect:/people";
    }

}
