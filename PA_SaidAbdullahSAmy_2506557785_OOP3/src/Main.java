import java.awt.*;
import java.util.ArrayList;
import java.util.List;

interface Soundable {
    void makeSound();
}
class Fairy implements Soundable {
    public void makeSound(){
        System.out.println("Kyaa~!");
    }

}
class Boss implements Soundable {
    public void makeSound(){
        System.out.println("You dare Challenge me!");
    }
}

public class Main{
    public static void main(String args[]){
        Fairy fairyObject = new Fairy();
        Boss bossObject = new Boss();
        List<Soundable> list = new ArrayList<>();
        list.add(fairyObject);
        list.add(bossObject);

        for (Soundable test : list){
            test.makeSound();
        }
    }
}