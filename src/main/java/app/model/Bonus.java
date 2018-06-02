package app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yinnon on 01/06/2018.
 */

public class Bonus  implements Serializable {

    private int bonusPoints;
    private Date date;

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
