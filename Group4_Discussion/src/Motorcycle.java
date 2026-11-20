public class Motorcycle extends Vehicle {
    String motorType;
    int numberofWheels;
    float maximumSpeed;

    public Motorcycle(String motorType,int speed, int numWheels, float maxSpeed){
        super(speed);
        this.motorType = motorType;
        this.numberofWheels = numWheels;
        this.maximumSpeed = maxSpeed;
    }

    @Override public void start(){
        System.out.println("This Motor is a " + motorType);
        System.out.println("This Motor has " + numberofWheels + " Wheels");
        System.out.println("It can reach up to " + maximumSpeed + " Km/H");


    }
}