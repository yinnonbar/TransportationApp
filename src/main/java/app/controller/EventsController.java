package app.controller;

import app.TransportationUtils;
import app.forms.EventForm;
import app.forms.EventTypeAndDriverForm;
import app.model.events.Accident;
import app.model.events.Event;
import app.model.events.ParkingTicket;
import app.model.events.TrafficTicket;
import app.model.human_resources.Driver;
import app.repository.EmployeeRepository;
import app.repository.EventRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;
import app.service.EventsService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

/**
 * Created by yinnon on 31/05/2018.
 */
@RestController
public class EventsController {
    private static final Logger logger = Logger.getLogger(EventsController.class);
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventsService eventsService;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * registering a new accident
     * @param eventForm -
     *                  driverId
                        vehicleId
                        date;
                        street
                        city;
                        otherVehicleId
                        otherDriverId
                        trafficTicketCause (no need to pass for this)
                        amountOfFine( no need to pass for this)
     * @return Accident
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/registerAccident", method = RequestMethod.POST)
    public Accident registerAccident(@RequestBody EventForm eventForm)
    throws ConstraintViolationException, DataIntegrityViolationException{
        try{
            Accident accident = eventsService.addNewAccident(eventForm);
            return accident;
        }catch (ConstraintViolationException e){
            logger.error("names length must be >= 1 and <= 15");
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("name length must be >= 1 and <= 15 " , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("a field in the form is null ", e);
            throw new DataIntegrityViolationException("a field in the form is null "
                    + e.getMessage());
        }

    }

    /**
     * registering a new parking ticket
     * @param eventForm -
                        driverId
                        vehicleId
                        date;
                        street;
                        city;
                        otherVehicleId ( no need to pass for this)
                        otherDriverId ( no need to pass for this)
                        trafficTicketCause ( no need to pass for this)
                        amountOfFine
     * @return ParkingTicket
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/registerParkingTicket", method = RequestMethod.POST)
    public ParkingTicket registerParkingTicket(@RequestBody EventForm eventForm)
            throws ConstraintViolationException, DataIntegrityViolationException {
        try{
            ParkingTicket parkingTicket = eventsService.addNewParkingTicket(eventForm);
            return parkingTicket;
        }catch (ConstraintViolationException e){
            logger.error("names length must be >= 1 and <= 15 and fine >= 1", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("names length must be >= 1 and <= 15 and fine >= 1 " , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("a field in the form is null or amount is < 1 ", e);
            throw new DataIntegrityViolationException("a field in the form is null or amount is < 1 "
                    + e.getMessage());
        }
    }

    /**
     * registering a new traffic ticket
     * @param eventForm -
     *                  driverId
                        vehicleId
                        date;
                        street;
                        city;
                        otherVehicleId ( no need to pass for this)
                        otherDriverId ( no need to pass for this)
                        trafficTicketCause - one of: "Accident",
                                                    "TrafficTicket",
                                                    "ParkingTicket"
                        amountOfFine
     *
     * @return a new TrafficTicket
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/registerTrafficTicket", method = RequestMethod.POST)
    public TrafficTicket registerTrafficTicket(@RequestBody EventForm eventForm)
    throws ConstraintViolationException, DataIntegrityViolationException {
        try {
            TrafficTicket trafficTicket = eventsService.addNewTrafficTicket(eventForm);
            return trafficTicket;
        }catch (ConstraintViolationException e){
            logger.error("names length must be >= 1 and <= 15 and fine >= 1", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("names length must be >= 1 and <= 15 and fine >= 1 " , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("a field in the form is null or amount is < 1 ", e);
            throw new DataIntegrityViolationException("a field in the form is null or amount is < 1 "
                    + e.getMessage());
        }

    }

    /**
     * returns all accidents
     * @return a list of accidents
     */
    @RequestMapping("/getAllAccidents")
    public List<Event> getAllAccidents() {
        List<Event> events = eventsService.findAllAccidents();
        return events;
    }

    /**
     * returns all parking tickets
     * @return a list of parking tickets
     */
    @RequestMapping("/getAllParkingTickets")
    public List<Event> getAllParkingTickets() {
        List<Event> events = eventsService.findAllParkingTickets();
        return events;
    }

    /**
     * retuns all traffic tickets
     * @return a list of parking tickets
     */
    @RequestMapping("/getAllTrafficTickets")
    public List<Event> getAllTrafficTickets() {
        List<Event> events = eventsService.findAllTrafficTickets();
        return events;
    }

    /**
     * returns all events
     * @return - a list of all events
     */
    @RequestMapping("/getAllEvents")
    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    /**
     * delete an event from the system
     * @param eventId - event id to delete
     * @return - string if deleted or not
     * @throws EmptyResultDataAccessException
     */
    @RequestMapping(value = "/deleteEvent/{eventId}", method = RequestMethod.POST)
    public String deleteEvent(@PathVariable long eventId) throws EmptyResultDataAccessException {
        try{
            eventRepository.deleteById(eventId);
            return "event id: " + eventId + " was deleted from system";
        }catch (EmptyResultDataAccessException e){
            logger.error("event id: " + eventId + " doesn't exists", e);
            throw new EmptyResultDataAccessException(e.getMessage(), e.getActualSize());
        }
    }

    /**
     * get all events of specific type and driver
     * @param eventTypeAndDriverForm-
     *                              type - type of event
     *                              driver - driver id
     * @return - the list of events for a specific type and driver
     * @throws EmptyResultDataAccessException
     * @throws InputMismatchException
     */
    @RequestMapping(value = "/getEventsByTypeAndDriver/", method = RequestMethod.POST)
    public List<Event> getEventsByTypeAndDriver(@RequestBody EventTypeAndDriverForm eventTypeAndDriverForm)
            throws EmptyResultDataAccessException, InputMismatchException {

        Driver driver = (Driver) employeeRepository.findById(eventTypeAndDriverForm.getDriverId());
        if (driver == null) {
            logger.error("driverId: " + eventTypeAndDriverForm.getDriverId()
                    + " doesn't exists");
            throw new EmptyResultDataAccessException("driverId: " + eventTypeAndDriverForm.getDriverId()
                    + " doesn't exists", (int) eventTypeAndDriverForm.getDriverId());
        }
        if(!TransportationUtils.ALLOWED_EVENTS_TYPES.contains(eventTypeAndDriverForm.getType())){
            logger.error("type: " + eventTypeAndDriverForm.getType()
                    + " is not known to the system");
            throw new InputMismatchException(eventTypeAndDriverForm.getType() + " not recognized as " +
                    "event on the system");
        }
        List<Event> events = eventRepository.findByTypeAndDriver(eventTypeAndDriverForm.getType(), driver);
        return events;
    }

    /**
     * calc percentage of each type of event
     * @return - string of events type's percentage
     */
    @RequestMapping("/getEventsPercentage")
    public String getEventsPercentage() {
        return eventsService.showEventsPercentage();
    }
}
