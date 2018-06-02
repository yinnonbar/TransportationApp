package app.service;

import app.TransportationUtils;
import app.model.Bonus;
import app.model.human_resources.Driver;
import app.model.human_resources.Employee;
import app.repository.EmployeeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yinnon on 01/06/2018.
 */
@Service
public class BonusService {
    private static final Logger logger = Logger.getLogger(BonusService.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    public int calcBonusForDriverInGivenTimeFrame(Driver driver, Date startDate,
                                              Date endDate){
        int bonusInTimeFrame = 0;
        for (Bonus bonus : driver.getBonuses()){
            if (bonus.getDate().after(startDate) &&
                    bonus.getDate().before(endDate)){
                bonusInTimeFrame += bonus.getBonusPoints();
            }
        }
        int rv = bonusInTimeFrame * TransportationUtils.BONUS_FACTOR;
        logger.info("bonus for driver: " + driver.getId() + " is: " + rv);
        return rv;
    }

    public int calcCompanyBalanceInAGivenTimeFrame(Date startDate,
                                                  Date endDate){
        int bonusInTimeFrame = 0;
        List<Driver> drivers = (List<Driver>)(List<?>) employeeRepository.findByType("Driver");
        for (Driver driver : drivers){
            bonusInTimeFrame += calcBonusForDriverInGivenTimeFrame(driver, startDate, endDate);
        }
        logger.info("bonus for company is: " + bonusInTimeFrame);
        return bonusInTimeFrame;
    }
}
