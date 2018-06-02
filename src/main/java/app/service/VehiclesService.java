package app.service;

import app.model.Vehicle;
import app.model.events.Event;
import app.repository.EventRepository;
import app.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yinnon on 02/06/2018.
 */
@Service
@Transactional
public class VehiclesService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EventRepository eventRepository;

    public void removeVehicleFromSystem(long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        removeVehicleFromEvents(vehicle);
        vehicleRepository.deleteById(vehicleId);

    }

    private void removeVehicleFromEvents(Vehicle vehicle) {
        List<Event> events = eventRepository.findByVehicle(vehicle);
        for (Event event : events){
            eventRepository.delete(event);
        }
    }
}
