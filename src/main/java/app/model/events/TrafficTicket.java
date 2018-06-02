package app.model.events;

import app.model.TrafficTicketCause;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;

/**
 * Created by yinnon on 31/05/2018.
 */
@Entity
public class TrafficTicket extends Event{
    @Enumerated(EnumType.STRING)
    private TrafficTicketCause trafficTicketCause;
    @Column(nullable = false)
    @Min(1)
    private double amountOfFine;

    public TrafficTicketCause getTrafficTicketCause() {
        return trafficTicketCause;
    }

    public void setTrafficTicketCause(TrafficTicketCause trafficTicketCause) {
        this.trafficTicketCause = trafficTicketCause;
    }

    public double getAmountOfFine() {
        return amountOfFine;
    }

    public void setAmountOfFine(double amountOfFine) {
        this.amountOfFine = amountOfFine;
    }
}
