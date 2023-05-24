package ua.donetc.springmvc.config.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.donetc.springmvc.config.models.Book;
import ua.donetc.springmvc.config.models.Human;


import java.util.List;
import java.util.Optional;


@Component
public class BooksDAO {
    private static JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> showAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }


    public Book getBookById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream()
                .findAny()
                .orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, year) " +
                "VALUES (?,?,?)", book.getName(), book.getAuthor(), book.getBook_id());
    }


    public void updateBook(Book book, int id) {
        jdbcTemplate.update("UPDATE book SET name = ?, author =? , year = ? WHERE book_id = ?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }

    public Optional<Human> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT h.*  FROM book join human h on book.human_id = h.human_id WHERE book_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Human.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET human_id=NULL WHERE book_id=?", id);
    }

    public void assign(int id, Human selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET human_id=? WHERE book_id=?", selectedPerson.getHuman_id(), id);
    }
}
