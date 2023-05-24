package ua.donetc.springmvc.config.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.donetc.springmvc.config.dao.HumanDAO;
import ua.donetc.springmvc.config.models.Human;

@Controller
@RequestMapping("/humans")
public class HumanController {
    private final HumanDAO humanDAO;

    @Autowired
    public HumanController(HumanDAO humanDAO) {
        this.humanDAO = humanDAO;
    }
    @GetMapping()
    public String showAllHuman(Model model) {
        model.addAttribute("humans", humanDAO.index());
        return "human/index";

    }

    @GetMapping("/{id}")
    public String showHumanById(@PathVariable("id") int id, Model model){
        model.addAttribute("human", humanDAO.showHumanById(id));
        model.addAttribute("books", humanDAO.getBooksByHumanId(id));
        return "human/show-id";

    }

    @GetMapping("/new-human")
    public String newHuman(@ModelAttribute("human")Human human){
        return "human/new-human";
    }

    @PostMapping
    public String createHuman(@ModelAttribute("human")Human human){
        humanDAO.save(human);
        return "redirect:/humans";
    }

    @GetMapping("/{id}/edit-human")
    public String editHuman(Model model, @PathVariable("id") int id){
        model.addAttribute("human", humanDAO.showHumanById(id));
        return "/human/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("human") Human human, @PathVariable("id")int id){
        humanDAO.update(id, human);
        return "redirect:/humans";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        humanDAO.delete(id);
        return "redirect:/humans";
    }
}
