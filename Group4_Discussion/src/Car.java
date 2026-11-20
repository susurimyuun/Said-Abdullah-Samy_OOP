public class Car extends Vehicle {
    String carType;
    int numberOfDoors;
    int numberofWheels;
    float maximumSpeed;

    public Car(String carType,int speed, int numDoors, int numWheels, float maxSpeed){
        super(speed);
        this.carType = carType;
        this.numberOfDoors = numDoors;
        this.numberofWheels = numWheels;
        this.maximumSpeed = maxSpeed;
    }

    @Override public void start(){
        System.out.println("This Car is a " + carType);
        System.out.println("This cas has " + numberOfDoors + " Doors");
        System.out.println("This Car has " + numberofWheels + " Wheels");
        System.out.println("It can reach up to " + maximumSpeed + " Km/H");

    }
}