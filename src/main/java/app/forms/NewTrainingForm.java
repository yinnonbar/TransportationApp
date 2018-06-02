package app.forms;

import java.util.Date;

/**
 * Created by yinnon on 01/06/2018.
 */
public class NewTrainingForm {
    private String title;
    private String description;
    private Date startDate;
    private int maxParticipates;

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
}
