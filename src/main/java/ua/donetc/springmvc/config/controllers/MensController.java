package ua.donetc.springmvc.config.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.donetc.springmvc.config.dao.MensDAO;
import ua.donetc.springmvc.config.models.Mens;

import javax.validation.Valid;

@Controller
@RequestMapping("/mens")
public class MensController {

    private final MensDAO mensDAO;

    @Autowired
    public MensController(MensDAO mensDAO) {
        this.mensDAO = mensDAO;
    }

    @GetMapping()
    public String allMens(Model model) {
        model.addAttribute("allMens", mensDAO.allMens());
        return "/man/mens";
    }

    @GetMapping("/{id}")
    public String menById(@PathVariable("id") int id, Model model) {
        model.addAttribute("men", mensDAO.mensById(id));
        return "/man/men-id";
    }

    @GetMapping("/new-mans")
    public String newMan(@ModelAttribute("man")Mens mens){
        return "/man/new-man";
    }

    @PostMapping
    public String createMan(@ModelAttribute("man") @Valid Mens mens, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "man/new-man";
        }
        mensDAO.createMan(mens);
        return "redirect:/mens";
    }

    @GetMapping("/{id}/edit-man")
    public String editMan(Model model, @PathVariable("id") int id){
        model.addAttribute("man", mensDAO.mensById(id));
        return "/man/edit-man";
    }

    @PatchMapping("/{id}")
    public String updateMan(@ModelAttribute("man") @Valid Mens mens, BindingResult bindingResult,
                            @PathVariable("id") int id){
        if (bindingResult.hasErrors()){
            return "/man/edit-man";
        }
        mensDAO.updateMan(id, mens);
        return "redirect:/mens";

    }

    @DeleteMapping("/{id}")
    public String deleteMan(@PathVariable("id") int id){
        mensDAO.deleteMan(id);
        return "redirect:/mens";
    }


}
