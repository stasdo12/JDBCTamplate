package ua.donetc.springmvc.config.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
@Entity
public class Human {
    //default constructor
    public Human (){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "human_id")
    private int human_id;
    @NotEmpty(message = "FIO should be not empty")
    private String FIO;

    @Min(1)
    private int year_human;

    public Human(int human_id, String FIO, int year_human) {
        this.human_id = human_id;
        this.FIO = FIO;
        this.year_human = year_human;
    }

    public int getHuman_id() {
        return human_id;
    }

    public void setHuman_id(int human_id) {
        this.human_id = human_id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYear_human() {
        return year_human;
    }

    public void setYear_human(int year_human) {
        this.year_human = year_human;
    }
}
