package app.controller;

import app.forms.newVehicleForm;
import app.model.Vehicle;
import app.repository.VehicleRepository;
import app.service.VehiclesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by yinnon on 02/06/2018.
 */
@RestController
public class VehicleController {
    private static final Logger logger = Logger.getLogger(VehicleController.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehiclesService vehiclesService;

    /**
     * adding new vehicle to the system
     * @param newVehicleForm -
     *                       company
     *                       model
     *                       licenseNumber
     *
     * @return the new vehicle
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/addNewVehicle", method = RequestMethod.POST)
    public Vehicle addNewVehicle(@RequestBody newVehicleForm newVehicleForm)
            throws ConstraintViolationException, DataIntegrityViolationException {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(newVehicleForm.getLicenseNumber());
        vehicle.setCompany(newVehicleForm.getCompany());
        vehicle.setModel(newVehicleForm.getModel());
        try{
            vehicleRepository.save(vehicle);
            return vehicle;
        }catch (ConstraintViolationException e){
            logger.error("company name and model length must be >= 1 and <= 15");
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("company name and model length must be >= 1 and <= 15 "
                    + "company: " + newVehicleForm.getCompany()
                    + " model: " + newVehicleForm.getModel(), violations);
        }catch (DataIntegrityViolationException e){
            logger.error("data integrity violation, license number already" +
                    "exists " + newVehicleForm.getLicenseNumber()
                    + " or one of the fields are null", e);
            throw new DataIntegrityViolationException("data integrity violation, license number already" +
                    "exists " + newVehicleForm.getLicenseNumber()
                    + " or one of the fields are null" + e.getMessage());
        }
    }

    /**
     * deleting a vehicle from the system
     * @param vehicleId - vehicle id to delete
     * @return - string if deleted or not
     * @throws EmptyResultDataAccessException
     */
    @RequestMapping(value = "/deleteVehicle/{vehicleId}", method = RequestMethod.POST)
    public String deleteVehicle(@PathVariable long vehicleId) throws EmptyResultDataAccessException {
        try{
            vehiclesService.removeVehicleFromSystem(vehicleId);
            return "vehicle id: " + vehicleId + " was deleted from system";
        }catch (EmptyResultDataAccessException e){
            logger.error("vehicle id: " + vehicleId + " doesn't exists", e);
            throw new EmptyResultDataAccessException(e.getMessage(), e.getActualSize());
        }
    }

    /**
     * get all vehicles
     * @return - a list of all vehicles
     */
    @RequestMapping("/getAllVehicles")
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles;
    }
}
