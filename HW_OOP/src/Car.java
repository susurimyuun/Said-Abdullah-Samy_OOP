
public class Car extends Vehicle {
    private static final double HOURLY_RATE = 4000.0;

    public Car(String vehicleType, int hoursParked) {
        super(vehicleType, hoursParked);
    }

    @Override
    public double calculateFee() {
        return getHoursParked() * HOURLY_RATE;
    }
}
