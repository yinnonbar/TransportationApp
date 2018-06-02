package app.controller;

import app.forms.NewTrainingForm;
import app.forms.RegisterDriverToTrainingForm;
import app.model.Training;
import app.repository.TrainingRepository;
import app.service.TrainingsService;
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
 * Created by yinnon on 01/06/2018.
 */
@RestController
public class TrainingsController {
    private static final Logger logger = Logger.getLogger(TrainingsController.class);

    @Autowired
    private TrainingsService trainingsService;

    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * add new training to the system
     * @param newTrainingForm -
                                title
                                description
                                startDate
                                maxParticipates
     * @return the new training
     * @throws ConstraintViolationException
     * @throws DataIntegrityViolationException
     */
    @RequestMapping(value = "/addNewTraining", method = RequestMethod.POST)
    public Training addNewTraining(@RequestBody NewTrainingForm newTrainingForm)
    throws ConstraintViolationException, DataIntegrityViolationException{
        try{
            Training training = trainingsService.addNewTraining(newTrainingForm);
            logger.info("new training added successfully");
            return training;
        }catch (ConstraintViolationException e){
            logger.error("constraint not met", e);
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            throw new ConstraintViolationException("constraint not met " , violations);
        }catch (DataIntegrityViolationException e){
            logger.error("a field is null", e);
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    /**
     * registring a driver to a training
     * @param registerToDriverTrainingForm -
                                            driverId
                                            trainingId
     * @return - a string if registered successfully or not
     * @throws NullPointerException
     */
    @RequestMapping(value = "/registerDriverToTraining", method = RequestMethod.POST)
    public String registerDriverToTraining(@RequestBody RegisterDriverToTrainingForm registerToDriverTrainingForm)
    throws NullPointerException{
        try{
            String message = trainingsService.registerDriverToTraining(registerToDriverTrainingForm);
            return message;
        }catch (NullPointerException e){
            logger.error("null ptr in register driver to training", e);
            throw new NullPointerException("null ptr in register driver to training " + e.getMessage());
        }
    }

    /**
     * get all trainings for a given driver
     * @param driverId - driver id
     * @return - a list of training for the specific driver
     */
    @RequestMapping("/findTrainingsForDriver/{driverId}")
    public List<Training> findTrainingsForDriver(@PathVariable long driverId) {
        List<Training> trainings = trainingsService.findTrainingsForDriver(driverId);
        return trainings;
    }

    /**
     * get all trainings
     * @return - a list of all trainings
     */
    @RequestMapping("/getAllTrainings")
    public List<Training> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        return trainings;
    }

    /**
     * delete a training from the system
     * @param trainingId - training id to delete
     * @return - string if deleted or not
     * @throws EmptyResultDataAccessException
     */
    @RequestMapping(value = "/deleteTraining/{trainingId}", method = RequestMethod.POST)
    public String deleteTraining(@PathVariable long trainingId) throws EmptyResultDataAccessException {
        try{
            trainingRepository.deleteById(trainingId);
            return "training id: " + trainingId + " was deleted from system";
        }catch (EmptyResultDataAccessException e){
            logger.error("training id: " + trainingId + " doesn't exists", e);
            throw new EmptyResultDataAccessException(e.getMessage(), e.getActualSize());
        }
    }

}
