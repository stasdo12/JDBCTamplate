package ua.donetc.springmvc.config.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.springmvc.config.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        String s = "from Person name";
        List<Person> people = session.createQuery(s, Person.class)
                .getResultList();

        return people;
    }

    public Person show(@NotEmpty @Size String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,1);
    }

    public void save(Person person) {

    }

    public void update(int id, Person updatedPerson) {

    }

    public void delete(int id) {

    }
}