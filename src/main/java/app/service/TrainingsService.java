package app.service;

import app.TransportationUtils;
import app.forms.NewTrainingForm;
import app.forms.RegisterDriverToTrainingForm;
import app.model.Bonus;
import app.model.Training;
import app.model.human_resources.Driver;
//import app.repository.BonusRepository;
import app.repository.EmployeeRepository;
import app.repository.TrainingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yinnon on 01/06/2018.
 */
@Service
@Transactional
public class TrainingsService {
    private static final Logger logger = Logger.getLogger(TrainingsService.class);
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
//    @Autowired
//    private BonusRepository bonusRepository;

    public Training addNewTraining(NewTrainingForm newTrainingForm){
        Training training = new Training();
        training.setTitle(newTrainingForm.getTitle());
        training.setDescription(newTrainingForm.getDescription());
        training.setStartDate(newTrainingForm.getStartDate());
        training.setMaxParticipates(newTrainingForm.getMaxParticipates());
        trainingRepository.save(training);
        return training;
    }

    public String registerDriverToTraining(RegisterDriverToTrainingForm registerToDriverTrainingForm) {
        String message;
        Driver driver = (Driver) employeeRepository.findById(registerToDriverTrainingForm.getDriverId());
        Training training = trainingRepository.findById(registerToDriverTrainingForm.getTrainingId());
        if (training.getDrivers().contains(driver)){
            message = "driver: " + registerToDriverTrainingForm.getDriverId()
                    + " is already registered to training: "
                    + registerToDriverTrainingForm.getTrainingId();
            logger.info(message);
            return message;
        }else if (training.getMaxParticipates() <= training.getDrivers().size()){
            message = "training: " + registerToDriverTrainingForm.getTrainingId() + " is full";
            logger.info(message);
            return message;

        }else{
            training.getDrivers().add(driver);
            Bonus bonus = new Bonus();
            bonus.setBonusPoints(TransportationUtils.TRAINING_POINTS);
            bonus.setDate(new Date());
            driver.addBonus(bonus);
            employeeRepository.save(driver);
            trainingRepository.save(training);
            message = "driver: " + registerToDriverTrainingForm.getDriverId()
                    + " registered to training: "
                    + registerToDriverTrainingForm.getTrainingId();
            logger.info(message);
            return message;
        }
    }

    public List<Training> findTrainingsForDriver(long driverId) {
        Driver driver = (Driver) employeeRepository.findById(driverId);
        List<Training> trainings = trainingRepository.findByDrivers(driver);
        return trainings;
    }
}
