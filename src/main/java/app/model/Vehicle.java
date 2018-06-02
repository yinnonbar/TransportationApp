package app.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by yinnon on 31/05/2018.
 */

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"licenseNumber"})})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Size(min = 1, max = 15)
    private String company;
    @Column(nullable = false)
    @Size(min = 1, max = 15)
    private String model;
    @Column(nullable = false)
    private int licenseNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
