package app.service;

import app.model.Bonus;
import app.model.human_resources.Driver;
import app.repository.EmployeeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yinnon on 02/06/2018.
 */
@Component
public class ScheduledTasks {
    private static final Logger logger = Logger.getLogger(ScheduledTasks.class);
    private final static String FOLDER_PATH = "C:\\yinnon\\Transportation\\";
    private final static long ONE_MONTH_IN_MILLIS = 2592000000L;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BonusService bonusService;

    @Scheduled(cron="0 0 08 01 * ?")
    public void calcMonthlyBonus(){
        List<Driver> drivers = (List<Driver>) (List<?>) employeeRepository.findByType("Driver");
        List<String> lines = new ArrayList<>();
        for (Driver driver : drivers) {
            long nowInMillis = System.currentTimeMillis();
            long beforeMonth = nowInMillis - ONE_MONTH_IN_MILLIS;
            int monthlyBonus = bonusService.calcBonusForDriverInGivenTimeFrame
                    (driver, new Date(beforeMonth), new Date(nowInMillis));
            lines.add(driver.getId() + ","
                    + driver.getName() + ","
                    + monthlyBonus + "\n");
        }
        writeToCsvFile(lines);

    }

    private void writeToCsvFile(List<String> lines){
        long nowInMillis = System.currentTimeMillis();
        String month = new SimpleDateFormat("MM").format(new Date(nowInMillis));
        String csvFileName = FOLDER_PATH + month + ".csv";
        File csvFile = new File(csvFileName);
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFile))){
            for (String line : lines){
                writer.write(line);
            }
        }catch (FileNotFoundException e) {
            logger.error("file not found, make sure to change FOLDER_PATH as required in your path", e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
