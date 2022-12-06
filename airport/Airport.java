package airport;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import static airport.Constants.*;

public class Airport {
    public static final String[] allCallsigns = "WZZ23AY,RYR87RT,WZZ34BM,WZZ98IS,KLM23UF,DLH78RT,WZZ94HK,RYR72TB,RYR20PQ".split(",");

    // TODO (1): Create a thread pool called `pool` for all the threads.
    public static ExecutorService pool = Executors.newFixedThreadPool(10);
    // TODO (1): Create a queue called `requests` for filing IFR route clearances requests by pilots.
    public static BlockingQueue<String> requests = new LinkedBlockingDeque<>();
    // TODO (2): Create a map called `clearances` for storing the clearances by the delivery service.
    public static ConcurrentMap<String, Clearance> clearances = new ConcurrentHashMap<>();
    // TODO (3): Create a map called `tower` for storing the towers for each runway.
    public static ConcurrentMap<String, Tower> tower = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // TODO (1): Start a single delivery service.
        Airport.pool.execute(new Delivery());
        // TODO (3): Create, store and start towers.
        for (int i = 0; i < RUNWAYS.length; i++)
        {
            Tower t = new Tower(RUNWAYS[i]);
            Airport.pool.execute(t);
            tower.put(RUNWAYS[i], t);
        }
        // TODO (1): Start the pilots. Wait between `MIN_SLOT_DIFF` and `MAX_SLOT_DIFF` between any two pilots.
        for (int i = 0; i < allCallsigns.length; i++) {
            Airport.pool.execute(new Pilot(allCallsigns[i]));
            Airport.sleepMsec(MIN_SLOT_DIFF, MAX_SLOT_DIFF);
        }
        // TODO (1): Shut down the pool. After `TIMEOUT` milliseconds stop it immediately.
        Airport.pool.shutdown();
        try {
            Airport.pool.awaitTermination(DELIVERY_TIMEOUT, MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void requestClearance(String callsign) {
        // TODO (1): File a clearance into the queue.
        Airport.requests.add(callsign);
    }

    public static String receive(int timeout) {
        // TODO (1): Get a request from the queue. Do not wait more than `timeout` milliseconds for it.
        String callsign = null;
        try {
            callsign = Airport.requests.poll(timeout, MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return callsign;
    }

    public static void clearRoute(String callsign, String runway, int departureTime) {
        // TODO (2): Put the clearance into the map of the clearances.
        Clearance clearance = new Clearance(runway, departureTime);
        clearances.put(callsign, clearance);
    }

    public static Clearance readClearance(String callsign) {
        // TODO (2): Read a clearance from the map of the clearances.
        return clearances.remove(callsign);
        /*Clearance clearance = clearances.get(callsign);
        return clearance; */
    }

    public static void readyForDeparture(Pilot pilot, String runway) {
        // TODO (3): Communicate the pilot to the appropriate tower and wake it up.
        Tower t = tower.get(runway);
        synchronized (t) {
            t.requestTakeoff(pilot);
            t.notify();
        }

        //synchronized (tower.get(runway)) {
        //    tower.get(runway).notify();
        //}
       
    }

    public static void airborne(Pilot pilot, String runway) {
        // TODO (3): Wake up the tower again.
        Tower t = tower.get(runway);
        synchronized (t) {
            t.notify();
        }
         //tower.get(runway).notify();
    }

    public static void sleepMsec(int msec) {
        try {
            MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

    public static void sleepMsec(int minMsec, int maxMsec) {
		int msec = ThreadLocalRandom.current().nextInt(minMsec, maxMsec);
		sleepMsec(msec);
	}
}