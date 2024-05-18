package organisms.animals;
import organisms.Creature;

public class Wolf extends Animal {

    public Wolf() {
        super(9, 5, 0, null);
    }

    public Wolf(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

}
