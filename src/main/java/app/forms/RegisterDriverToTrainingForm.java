package app.forms;

/**
 * Created by yinnon on 01/06/2018.
 */
public class RegisterDriverToTrainingForm {
    private long driverId;
    private long trainingId;

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }
}
