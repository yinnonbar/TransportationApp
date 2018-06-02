package app.model;

import app.model.human_resources.Driver;
import app.model.human_resources.Manager;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.Date;
import java.util.List;

/**
 * Created by yinnon on 31/05/2018.
 */
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length=15)
    private String title;
    @Column(length=1000)
    private String description;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private int maxParticipates;
    @ManyToMany()
    @Enumerated(EnumType.STRING)
    private List<Driver> drivers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getMaxParticipates() {
        return maxParticipates;
    }

    public void setMaxParticipates(int maxParticipates) {
        this.maxParticipates = maxParticipates;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
