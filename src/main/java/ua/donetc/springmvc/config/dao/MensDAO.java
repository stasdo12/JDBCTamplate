package ua.donetc.springmvc.config.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.donetc.springmvc.config.models.Mens;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MensDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MensDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Mens> allMens() {
        return jdbcTemplate.query("SELECT * FROM mens", new BeanPropertyRowMapper<>(Mens.class));
    }

    public Mens mensById(int id) {
        return jdbcTemplate.query("SELECT * FROM mens WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Mens.class))
                .stream()
                .findAny()
                .orElse(null);

    }


    public void createMan(Mens mens) {
        jdbcTemplate.update("INSERT INTO mens(age, name, email) VALUES ( ?, ?, ?)", mens.getAge(),
                mens.getName(), mens.getEmail());
    }


    public void updateMan(int id, Mens mens) {
        jdbcTemplate.update("UPDATE mens SET  name=?, age = ?, email = ? WHERE id = ?",
                mens.getName(), mens.getAge(), mens.getEmail(), id);
    }

    public void deleteMan(int id) {
        jdbcTemplate.update("DELETE  FROM mens WHERE id = ?", id);
    }

    public void testMultipleUpdate() {
        List<Mens> mensList =  create1000Mens();
        long before = System.currentTimeMillis();

        for (Mens men: mensList) {
            jdbcTemplate.update("INSERT INTO mens(age, name, email) VALUES ( ?, ?, ?)", men.getAge(),
                    men.getName(), men.getEmail());
        }
        long after = System.currentTimeMillis();
        long time = before-after;
        System.out.println("Time  "  + time);
    }

    public void testBatchUpdate() {
        List<Mens> mensList = create1000Mens();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO mens(age, name, email) VALUES ( ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(2, mensList.get(i).getAge());
                preparedStatement.setString(3, mensList.get(i).getName());
                preparedStatement.setString(4, mensList.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return mensList.size();
            }
        });

        long after = System.currentTimeMillis();
        long time = before-after;
        System.out.println("Time  "  + time);

    }
    private List<Mens> create1000Mens() {
        List<Mens> mensList = new ArrayList<>();
        for (int i =0; i<1000; i++){
            mensList.add(new Mens(i,  ++i, "name"+i, "email"+i));
        }
        return mensList;
    }
}
