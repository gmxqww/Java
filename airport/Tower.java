package airport;

import static airport.Constants.*;

public class Tower implements Runnable {
    private final String runway;

    private Pilot request = null;

    Tower(String runway) {
        this.runway = runway;
    }

    public void requestTakeoff(Pilot pilot) {
        request = pilot;
    }

    @Override
    public void run() {

        while (true) {
            // TODO (3): Wait for a pilot requesting takeoff. Do not wait more than `AIRPORT_TIMEOUT`.
            //           If no pilot requests takeoff, exit the loop.
            if (request == null) {
                synchronized (this) {
                    try {
                        wait(AIRPORT_TIMEOUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (request == null) {
                    break;
                }
            }
            System.out.println(runway + "_TWR: " + request.getCallSign() + " cleared for takeoff runway " + runway + ".");
            request = null;
            // TODO (3): Wait for the pilot reporting airborne.
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}