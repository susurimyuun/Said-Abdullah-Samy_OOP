
public class ParkingGateSystem {
    public static void printTicket(Vehicle vehicle) {
        System.out.println("----- Parking Ticket -----");
        System.out.println("Vehicle Type : " + vehicle.getVehicleType());
        System.out.println("Duration     : " + vehicle.getHoursParked() + " hours");
        System.out.printf("Total Fee    : Rp.%.2f%n", vehicle.calculateFee());
        System.out.println("--------------------------\n");
    }

    public static void main(String[] args) {
        Vehicle[] vehicles = new Vehicle[] {
                new Motorcycle("Motor Ninja", 3),
                new Car("Mobil BMW", 4),
                new Truck("Folfo Truck", 2)
        };

        for (Vehicle v : vehicles) {
            printTicket(v);
        }
    }
}
