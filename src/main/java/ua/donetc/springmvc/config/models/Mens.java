package ua.donetc.springmvc.config.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Entity
public class Mens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = 0, message = "Age should be a positive")
    private int age;

    @Size(min = 2, max = 30, message = "Size of name should be between 2 and 30")
    @NotEmpty(message = "Name should be not empty ")
    private String  name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "It's not a valid form")
    private String email;

    public Mens (){

    }

    public Mens(int id, int age, String name, String email) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
