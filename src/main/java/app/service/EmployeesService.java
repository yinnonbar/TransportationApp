package app.service;

import app.forms.NewEmployeeForm;
import app.model.Training;
import app.model.events.Event;
import app.model.human_resources.Driver;
import app.model.human_resources.Manager;
import app.repository.EmployeeRepository;
import app.repository.EventRepository;
import app.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yinnon on 31/05/2018.
 */

@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private EventRepository eventRepository;

    public Driver addNewDriver(NewEmployeeForm newEmployeeForm) {
        Driver driver = new Driver();
        driver.setName(newEmployeeForm.getName());
        employeeRepository.save(driver);
        return driver;
    }

    public Manager addNewManager(NewEmployeeForm newEmployeeForm) {
        Manager manager = new Manager();
        manager.setName(newEmployeeForm.getName());
        employeeRepository.save(manager);
        return manager;
    }

    public void removeDriverFromSystem(long driverId) {
        Driver driver = (Driver) employeeRepository.findById(driverId);
        removeDriverTrainings(driver);
        removeDriverEvents(driver);
        employeeRepository.deleteById(driverId);
    }

    private void removeDriverTrainings(Driver driver){
        List<Training> trainings = trainingRepository.findByDrivers(driver);
        for (Training training : trainings) {
            training.getDrivers().remove(driver);
            trainingRepository.save(training);
        }
    }

    private void removeDriverEvents(Driver driver){
        List<Event> events = eventRepository.findByDriver(driver);
        for (Event event : events){
            eventRepository.delete(event);
        }
    }
}
