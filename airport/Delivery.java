package airport;

import static airport.Constants.*;

import java.util.concurrent.ThreadLocalRandom;

public class Delivery implements Runnable {

    @Override
    public void run() {
        while(true) {
            String callsign = Airport.receive(DELIVERY_TIMEOUT);
            if (callsign == null) {
                break;
            }
            Airport.sleepMsec(MIN_CLEARANCE_PROCESSING_TIME, MAX_CLEARANCE_PROCESSING_TIME);
            String runway = RUNWAYS[ThreadLocalRandom.current().nextInt(RUNWAYS.length)];
            int departureTime = ThreadLocalRandom.current().nextInt(MAX_DEPARTURE_TIME - MIN_DEPARTURE_TIME) + MIN_DEPARTURE_TIME;
            System.out.println("DEL: " + callsign + " cleared to destination as filed, departure runway " + runway +
                    ", expect departure in " + departureTime + " minutes.");
            Airport.clearRoute(callsign, runway, departureTime);
        }
    }
    
}
