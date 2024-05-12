package organisms.animals;
import organisms.Creature;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Animal extends Creature {

    public Animal() {
        super(0, 0, 0, null);
    }

    public Animal(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    protected Point move_target() {
        // move the animal
        // done as separate function, so I can override it later
        List<Point> neighbours = my_world.get_neighbours(this.position, false);

        if (neighbours.isEmpty()){
            return this.position;
        }

        Random rnd = new Random();
        int pos = rnd.nextInt(neighbours.size());

        return neighbours.get(pos);
    }

    protected boolean reproduce() {
        // reproduce the animal
        List<Point> neighbours = my_world.get_neighbours(this.position, false);

        if (neighbours.isEmpty()){
            return false;
        }

        Random rnd = new Random();
        int pos = rnd.nextInt(neighbours.size());
        Point new_position = neighbours.get(pos);
        Creature creature = create_creature(this.getClass());
        creature.set_position(new_position);
        my_world.add_creature(creature);

        return true;
    }


    @Override
    public String toString() {
        return "Animal{" +
                "type=" + this.getClass().getSimpleName() +
                ", power=" + power +
                ", initiative=" + initiative +
                ", age=" + age +
                ", position=" + position +
                ", my_world=" + my_world +
                '}';
    }
}
