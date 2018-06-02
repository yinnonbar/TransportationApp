package app.model.events;

import app.model.Vehicle;
import app.model.human_resources.Driver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by yinnon on 31/05/2018.
 */
@Entity
public class Accident extends Event {
    @ManyToOne
    private Vehicle otherVehicle;
    @ManyToOne
    private Driver otherDriver;

    public Vehicle getOtherVehicle() {
        return otherVehicle;
    }

    public void setOtherVehicle(Vehicle otherVehicle) {
        this.otherVehicle = otherVehicle;
    }

    public Driver getOtherDriver() {
        return otherDriver;
    }

    public void setOtherDriver(Driver otherDriver) {
        this.otherDriver = otherDriver;
    }

}
