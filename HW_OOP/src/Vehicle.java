// Abstract Parent Class
public abstract class Vehicle {
    private String vehicleType;
    private int hoursParked;

    public Vehicle(String vehicleType, int hoursParked) {
        this.vehicleType = vehicleType;
        this.hoursParked = hoursParked;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getHoursParked() {
        return hoursParked;
    }

    public void setHoursParked(int hoursParked) {
        this.hoursParked = hoursParked;
    }

    // Abstract method to enforce polymorphic rate calculation
    public abstract double calculateFee();
}

