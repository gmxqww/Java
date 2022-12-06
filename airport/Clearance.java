package airport;

public class Clearance {
    public String runway;
    public int departureTime;

    public Clearance(String runway, int takeoffTime) {
        this.runway = runway;
        this.departureTime = takeoffTime;
    }
}
