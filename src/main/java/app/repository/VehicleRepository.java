package app.repository;

import app.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;

/**
 * Created by yinnon on 01/06/2018.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
        Vehicle findById(long id);
        Vehicle deleteById(long id);
        @Column(unique = true)
        Vehicle findByLicenseNumber(int licenseNumber);
}
