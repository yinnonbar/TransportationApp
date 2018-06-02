package app.service;

import app.TransportationUtils;
import app.forms.EventForm;
import app.model.Bonus;
import app.model.Vehicle;
import app.model.events.Accident;
import app.model.events.Event;
import app.model.events.ParkingTicket;
import app.model.events.TrafficTicket;
import app.model.human_resources.Driver;

import app.repository.EmployeeRepository;
import app.repository.EventRepository;
import app.repository.VehicleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by yinnon on 31/05/2018.
 */
@Service
public class EventsService {
    private static final Logger logger = Logger.getLogger(EventsService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Accident addNewAccident(EventForm eventForm){
        Accident accident = new Accident();
        setEventParams(accident, eventForm, TransportationUtils.ACCIDENT_POINTS);
        Vehicle otherVehicle = vehicleRepository.findById(eventForm.getOtherVehicleId());
        accident.setOtherVehicle(otherVehicle);
        Driver otherDriver = (Driver) employeeRepository.findById(eventForm.getOtherDriverId());
        accident.setOtherDriver(otherDriver);
        eventRepository.save(accident);
        return accident;
    }

    public ParkingTicket addNewParkingTicket(EventForm eventForm)
    throws ConstraintViolationException{
        ParkingTicket parkingTicket = new ParkingTicket();
        setEventParams(parkingTicket, eventForm, TransportationUtils.PARKING_POINTS);
        parkingTicket.setAmountOfFine(eventForm.getAmountOfFine());
        try{
            eventRepository.save(parkingTicket);
        }catch (ConstraintViolationException e){
            logger.error("constrain error for a value", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("constrain error for a value " , violations);
        }catch (TransactionSystemException e){
            logger.error("transaction error", e);
            throw new TransactionSystemException(e.getMessage());
        }
        return parkingTicket;
    }

    public TrafficTicket addNewTrafficTicket(EventForm eventForm) {
        TrafficTicket trafficTicket = new TrafficTicket();
        setEventParams(trafficTicket, eventForm, TransportationUtils.TRAFFIC_POINTS);
        trafficTicket.setAmountOfFine(eventForm.getAmountOfFine());
        trafficTicket.setTrafficTicketCause(eventForm.getTrafficTicketCause());
        try{
            eventRepository.save(trafficTicket);
        }catch (ConstraintViolationException e){
            logger.error("constrain error for a value", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("constrain error for a value " , violations);
        }catch (TransactionSystemException e){
            logger.error("transaction error", e);
            throw new TransactionSystemException(e.getMessage());
        }

        return trafficTicket;
    }

    private void setEventParams(Event event, EventForm eventForm, int bonusPoints){
        try{
            Driver driver = (Driver) employeeRepository.findById(eventForm.getDriverId());
            Bonus bonus = new Bonus();
            bonus.setBonusPoints(bonusPoints);
            bonus.setDate(eventForm.getDate());
            driver.addBonus(bonus);
            employeeRepository.save(driver);
            event.setDriver(driver);
            Vehicle vehicle = vehicleRepository.findById(eventForm.getVehicleId());
            event.setVehicle(vehicle);
            event.setDate(eventForm.getDate());
            event.setStreet(eventForm.getStreet());
            event.setCity(eventForm.getCity());
        }catch (ConstraintViolationException e){
            logger.error("constrain error for a value", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("constrain error for a value " , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("data integrity error", e);
            throw new DataIntegrityViolationException("data integrity error " + e.getMessage());
        }catch (NullPointerException e){
            logger.error("null pointer : ", e);
            throw new NullPointerException(e.getMessage());
        }

    }

    public List<Event> findAllAccidents() {
        return eventRepository.findByType(Accident.class.getSimpleName());
    }

    public List<Event> findAllParkingTickets() {
        return eventRepository.findByType(ParkingTicket.class.getSimpleName());
    }

    public List<Event> findAllTrafficTickets() {
        return eventRepository.findByType(TrafficTicket.class.getSimpleName());
    }

    public String showEventsPercentage() {
        int numOfEvents = eventRepository.findAll().size();
        if (numOfEvents == 0) {
            return "No events yet";
        } else {
            int numOfAccidents = findAllAccidents().size();
            float accidentsPercentage = numOfAccidents * 100 / numOfEvents;
            int numOfParkingTickets = findAllParkingTickets().size();
            float parkingTicketsPercentage = numOfParkingTickets * 100 / numOfEvents;
            int numOfTrafficTickets = findAllTrafficTickets().size();
            float trafficTicketsPercentage = numOfTrafficTickets * 100 / numOfEvents;
            return "accidents: " + accidentsPercentage + "% "
                    + "traffic tickets: " + trafficTicketsPercentage + "% "
                    + "parking tickets: " + parkingTicketsPercentage + "%";
        }
    }
}
