package airport;

import static airport.Constants.*;

public class Pilot implements Runnable {
    private String callsign;
    private int gate;
    private static int nextGate = 0;

    Pilot(String callsign) {
        this.callsign = callsign;
        // TODO (1): Initialize field `gate` from a counter, always assigning a unique number.
        this.gate = nextGate++;
    }

    public String getCallSign() {
        return callsign;
    }

    @Override
    public void run() {
        Airport.sleepMsec(MIN_PREPARATION_TIME, MAX_PREPARATION_TIME);
        System.out.println(callsign + ": Gate " + gate + ", requesting IFR deperature clearance.");
        Airport.requestClearance(callsign);
        // Uncomment this for (2)
        while (true) {
            // TODO (2): Poll the clearance periodically by waiting `TIME_BETWEEN_POLLS` between each two polls.
            //           Continue the loop if the clearance is not received yet.
            Clearance clearance = Airport.readClearance(callsign);
            if (clearance == null) {
                Airport.sleepMsec(TIME_BETWEEN_POOLS);
                continue;
            }else{
            Airport.sleepMsec(MIN_READBACK_TIME, MAX_READBACK_TIME);
            System.out.println(callsign + ": cleared to destination as filed, departure runway " + clearance.runway +
                    ", expecting departure in " + clearance.departureTime + " seconds.");
               // break;
                }
        // Uncomment this for (3)
            Airport.sleepMsec(clearance.departureTime);
            System.out.println(callsign + ": " + clearance.runway + " tower, ready for departure.");
            Airport.readyForDeparture(this, clearance.runway);
            Airport.sleepMsec(MIN_TAKEOFF_TIME, MAX_TAKEOFF_TIME);
            System.out.println(callsign + ": " + clearance.runway + " tower, airborne.");
            Airport.airborne(this, clearance.runway);
            break;
        }
    }
}
