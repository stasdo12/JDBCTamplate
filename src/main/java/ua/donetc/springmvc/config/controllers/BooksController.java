package ua.donetc.springmvc.config.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.donetc.springmvc.config.dao.BooksDAO;
import ua.donetc.springmvc.config.dao.HumanDAO;
import ua.donetc.springmvc.config.models.Book;
import ua.donetc.springmvc.config.models.Human;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksDAO booksDAO;
    private final HumanDAO humanDAO;

    @Autowired
    public BooksController(BooksDAO booksDAO, HumanDAO humanDAO) {
        this.booksDAO = booksDAO;
        this.humanDAO = humanDAO;
    }
    @GetMapping()
    public String index(Model model) {
       model.addAttribute("books",booksDAO.showAllBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, Model model, @ModelAttribute("human") Human human) {
        model.addAttribute("book", booksDAO.getBookById(id));

        Optional<Human> bookOwner = booksDAO.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("humans", humanDAO.index());
        return "books/show";
    }

    @GetMapping("/new-book")
    public String newBook(@ModelAttribute("book")Book book){
        return "/books/new-book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") Book book){
        booksDAO.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}/edit-book")
    public String editBook(Model model,  @PathVariable("id") int id){
        model.addAttribute("book", booksDAO.getBookById(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id")int id){
        booksDAO.updateBook(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        booksDAO.deleteBook(id);
        return "redirect:/books";

    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksDAO.release(id);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("human") Human selectedPerson) {
        booksDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }


}
