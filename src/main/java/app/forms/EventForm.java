package app.forms;

import app.model.TrafficTicketCause;

import java.util.Date;

/**
 * Created by yinnon on 01/06/2018.
 */
public class EventForm {
    private long driverId;
    private long vehicleId;
    private Date date;
    private String street;
    private String city;
    private long otherVehicleId;
    private long otherDriverId;
    private TrafficTicketCause trafficTicketCause;
    private double amountOfFine;

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getOtherVehicleId() {
        return otherVehicleId;
    }

    public void setOtherVehicleId(long otherVehicleId) {
        this.otherVehicleId = otherVehicleId;
    }

    public long getOtherDriverId() {
        return otherDriverId;
    }

    public void setOtherDriverId(long otherDriverId) {
        this.otherDriverId = otherDriverId;
    }

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
