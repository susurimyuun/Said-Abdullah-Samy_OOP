import java.util.*;

class GameObject {
    String name;
    boolean destroyed;

    GameObject(String name, boolean destroyed) {
        this.name = name;
        this.destroyed = destroyed;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Main {

    public static <T extends GameObject, Gameobject> void removeDestroyed(List<T> objects) {
        // Complete this method
        Iterator<T> itil = objects.iterator();
        while (itil.hasNext()){
            T object = itil.next();
            if (object.destroyed){
                itil.remove();
            }
        }
    }

    public static void main(String[] args) {
        List<GameObject> objects = new ArrayList<>();

        objects.add(new GameObject("Player", false));
        objects.add(new GameObject("Enemy", true));
        objects.add(new GameObject("Coin", false));
        objects.add(new GameObject("Boss", true));
        objects.add(new GameObject("NPC", false));

        removeDestroyed(objects);

        System.out.println("Active objects:");
        for (GameObject object : objects) {
            System.out.println(object);
        }
    }
}
