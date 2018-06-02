package app.controller;

import app.forms.BonusForDriverCalcForm;
import app.forms.NewEmployeeForm;
import app.forms.TimeFrameForm;
import app.model.human_resources.Driver;
import app.model.human_resources.Manager;
import app.repository.EmployeeRepository;
import app.service.BonusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import app.service.EmployeesService;
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by yinnon on 31/05/2018.
 */
@RestController
public class EmployeesController {
    private static final Logger logger = Logger.getLogger(EmployeesController.class);
    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BonusService bonusService;

    /**
     * hiring a new driver
     * @param newEmployeeForm - name of the new driver
     * @return new driver
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/hireNewDriver", method = RequestMethod.POST)
    public Driver hireNewDriver(@RequestBody NewEmployeeForm newEmployeeForm)
    throws ConstraintViolationException, DataIntegrityViolationException{
        try{
            Driver driver = employeesService.addNewDriver(newEmployeeForm);
            logger.info("new driver created successfully");
            return driver;
        }catch (ConstraintViolationException e){
            logger.error("name length must be >= 1 and <= 15");
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("name length must be >= 1 and <= 15 " + newEmployeeForm.getName() , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("name is null", e);
            throw new DataIntegrityViolationException("name is null");
        }
    }

    /**
     * hiring a new manager
     * @param newEmployeeForm - name of the new manager
     * @return the new manager
     */
    @RequestMapping(value = "/hireNewManager", method = RequestMethod.POST)
    public Manager hireNewManager(@RequestBody NewEmployeeForm newEmployeeForm) {
        try{
            Manager manager = employeesService.addNewManager(newEmployeeForm);
            logger.info("new manager created successfully");
            return manager;
        }catch (ConstraintViolationException e){
            logger.error("name length must be >= 1 and <= 15");
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("name length must be >= 1 and <= 15 " + newEmployeeForm.getName() , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("name is null", e);
            throw new DataIntegrityViolationException("name is null " + e);
        }
    }

    /**
     * delete driver from the system
     * @param driverId - the driver ID
     * @return string if delete succeed or not
     * @throws EmptyResultDataAccessException
     */
    @RequestMapping(value = "/deleteDriver/{driverId}", method = RequestMethod.POST)
    public String deleteDriver(@PathVariable long driverId) throws EmptyResultDataAccessException{
        try{
            employeesService.removeDriverFromSystem(driverId);
            return "driver id: " + driverId + " was deleted from system";
        }catch (EmptyResultDataAccessException e){
            logger.error("driver id: " + driverId + " doesn't exists", e);
            throw new EmptyResultDataAccessException(e.getMessage(), e.getActualSize());
        }
    }

    /**
     * calculating bonus for a given driver in a given time frame
     * @param bonusForDriverCalcForm -
     *                               *driverId
     *                               *startDate
     *                               *endDate
     * @return - int representing the bonus
     * @throws EntityExistsException
     * @throws NullPointerException
     */
    @RequestMapping(value = "/calcBonusForDriver", method = RequestMethod.POST)
    public int calcBonusForDriver(@RequestBody BonusForDriverCalcForm bonusForDriverCalcForm)
            throws EntityExistsException, NullPointerException{
        Driver driver = (Driver) employeeRepository.findById(bonusForDriverCalcForm.getDriverId());
        if (driver == null){
            logger.error("wrong driver ID given" + bonusForDriverCalcForm.getDriverId());
            throw new EntityExistsException("wrong driver ID given" + bonusForDriverCalcForm.getDriverId());
        }else if (bonusForDriverCalcForm.getStartDate() == null
                || bonusForDriverCalcForm.getEndDate() == null){
            logger.error("missing date time frame");
            throw new NullPointerException("missing date time frame");
        }
        else{
            return bonusService.calcBonusForDriverInGivenTimeFrame(driver,
                    bonusForDriverCalcForm.getStartDate(),
                    bonusForDriverCalcForm.getEndDate());
        }
    }

    /**
     * calc company's bonus in a time frame
     * @param timeFrameForm -
     *                      *startDate
     *                      *endDate
     * @return - int representing the company's bonus
     * @throws NullPointerException
     */
    @RequestMapping(value = "/calcCompanyBalanceInAGivenTimeFrame", method = RequestMethod.POST)
    public int calcCompanyBalanceInAGivenTimeFrame(@RequestBody TimeFrameForm timeFrameForm)
    throws NullPointerException {
        if (timeFrameForm.getStartDate() == null
                || timeFrameForm.getEndDate() == null){
            logger.error("missing date time frame");
            throw new NullPointerException("missing date time frame");
        }
        return bonusService.calcCompanyBalanceInAGivenTimeFrame(
                timeFrameForm.getStartDate(),
                timeFrameForm.getEndDate());
    }

    /**
     * get all drivers from the system
     * @return - list of Drivers
     */
    @RequestMapping("/getAllDrivers")
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = (List<Driver>) (List<?>) employeeRepository.findByType("Driver");
        return drivers;
    }
}
