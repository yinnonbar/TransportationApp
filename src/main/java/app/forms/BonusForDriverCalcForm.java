package app.forms;

import java.util.Date;

/**
 * Created by yinnon on 01/06/2018.
 */
public class BonusForDriverCalcForm {
    private long driverId;
    private Date startDate;
    private Date endDate;

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
