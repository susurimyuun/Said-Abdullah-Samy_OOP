import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String args[]){
        Car car1 = new Car("BMW",200,4,4,200);
        Motorcycle motor1 = new Motorcycle("Yamaha",160,2,160);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(car1);
        vehicleList.add(motor1);
        for (Vehicle v: vehicleList){
            v.start();
        }
    }
}