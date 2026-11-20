
public class Motorcycle extends Vehicle {
    private static final double HOURLY_RATE = 3000.0;

    public Motorcycle(String vehicleType, int hoursParked) {
        super(vehicleType, hoursParked);
    }

    @Override
    public double calculateFee() {
        return getHoursParked() * HOURLY_RATE;
    }
}
