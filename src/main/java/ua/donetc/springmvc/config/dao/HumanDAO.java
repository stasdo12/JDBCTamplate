package ua.donetc.springmvc.config.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.donetc.springmvc.config.models.Book;
import ua.donetc.springmvc.config.models.Human;

import java.util.List;

@Component
public class HumanDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HumanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Human> index() {
         return jdbcTemplate.query("SELECT * FROM human", new BeanPropertyRowMapper<>(Human.class));
    }

    public Human showHumanById(int id) {
        return jdbcTemplate.query("SELECT * FROM human WHERE human_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Human.class)).stream()
                .findAny()
                .orElse(null);
    }

    public void save(Human human) {
        jdbcTemplate.update("INSERT INTO human(fio, year_human) VALUES (?, ?)",
                human.getFIO(), human.getYear_human());

    }

    public void update(int id, Human human) {
        jdbcTemplate.update("UPDATE human SET fio = ?, year_human = ? WHERE human_id = ?",
                human.getFIO(), human.getYear_human(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM human WHERE human_id =?", id);
    }

    public List<Book> getBooksByHumanId(int id) {
        return jdbcTemplate.query("SELECT * FROM book Where human_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
