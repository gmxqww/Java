package airport;

public class Constants {
    static final int MIN_SLOT_DIFF = 200;
    static final int MAX_SLOT_DIFF = 1000;
    static final int MIN_PREPARATION_TIME = 500;
    static final int MAX_PREPARATION_TIME = 1500;
    static final int MIN_CLEARANCE_PROCESSING_TIME = 400;
    static final int MAX_CLEARANCE_PROCESSING_TIME = 800;
    static final int DELIVERY_TIMEOUT = 3000;
    static final int AIRPORT_TIMEOUT = 5000;
    static final int MIN_DEPARTURE_TIME = 500;
    static final int MAX_DEPARTURE_TIME = 1500;
    static final int TIME_BETWEEN_POOLS = 100;
    static final int MIN_READBACK_TIME = 200;
    static final int MAX_READBACK_TIME = 400;
    static final int MIN_TAKEOFF_TIME = 300;
    static final int MAX_TAKEOFF_TIME = 600;

    static final String[] RUNWAYS = "31L,31R".split(",");
}