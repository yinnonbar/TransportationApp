package app.repository;

import app.model.events.Event;
import app.model.human_resources.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yinnon on 01/06/2018.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long id);
    @Query("select a from Employee a where type = ?1")
    List<Employee> findByType(String type);
    Employee findByName(String name);
    Employee deleteById(long id);
}