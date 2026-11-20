
public class Truck extends Vehicle {
    private static final double HOURLY_RATE = 8000.0;

    public Truck(String vehicleType, int hoursParked) {
        super(vehicleType, hoursParked);
    }

    @Override
    public double calculateFee() {
        return getHoursParked() * HOURLY_RATE;
    }
}
