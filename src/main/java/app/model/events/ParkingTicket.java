package app.model.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

/**
 * Created by yinnon on 31/05/2018.
 */

@Entity
public class ParkingTicket extends Event{
    @Column(nullable = false)
    @Min(1)
    private double amountOfFine;

    public double getAmountOfFine() {
        return amountOfFine;
    }

    public void setAmountOfFine(double amountOfFine) {
        this.amountOfFine = amountOfFine;
    }
}
