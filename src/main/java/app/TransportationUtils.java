package app;

import app.model.events.Accident;
import app.model.events.ParkingTicket;
import app.model.events.TrafficTicket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinnon on 02/06/2018.
 */
public class TransportationUtils {
    public final static int ACCIDENT_POINTS = -3;
    public final static int TRAFFIC_POINTS = -2;
    public final static int PARKING_POINTS = -1;
    public final static int TRAINING_POINTS = 1;

    public final static int BONUS_FACTOR = 100;

    public static List<String> ALLOWED_EVENTS_TYPES = new ArrayList<String>() {
        {
            add(Accident.class.getSimpleName());
            add(TrafficTicket.class.getSimpleName());
            add(ParkingTicket.class.getSimpleName());
        }
    };



    }


