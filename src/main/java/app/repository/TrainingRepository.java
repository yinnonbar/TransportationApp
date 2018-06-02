package app.repository;

import app.model.Training;
import app.model.Vehicle;
import app.model.human_resources.Driver;
import app.model.human_resources.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yinnon on 01/06/2018.
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training findById(long id);
    Training deleteById(long id);
    List<Training> findByDrivers(Driver driver);
}
