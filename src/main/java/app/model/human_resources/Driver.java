package app.model.human_resources;

import app.model.Bonus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yinnon on 31/05/2018.
 */
@Entity
public class Driver extends Employee{
//    @OneToMany

    @Lob
    private ArrayList<Bonus> bonuses = new ArrayList<Bonus>();

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void addBonus(Bonus bonus){
        bonus.setBonusPoints(bonus.getBonusPoints());
        bonus.setDate(bonus.getDate());
        bonuses.add(bonus);
    }
}
