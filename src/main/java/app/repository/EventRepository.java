package app.repository;

import app.model.Vehicle;
import app.model.events.Event;
import app.model.human_resources.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yinnon on 01/06/2018.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
    @Query("select a from Event a where type = ?1")
    List <Event> findByType(String type);
    Event deleteById(long id);
    List<Event> findByDriver(Driver driver);
    @Query("select a from Event a where type = ?1 and driver=?2")
    List<Event> findByTypeAndDriver(String type, Driver driver);
    List<Event> findByVehicle(Vehicle driver);
}
