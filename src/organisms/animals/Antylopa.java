package organisms.animals;

import organisms.Creature;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Antylopa extends Creature {
    public Antylopa() {
        super(4, 4, 0, null);
    }

    public Antylopa(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    private boolean run_away(){
        Random rand = new Random();
        boolean will_run = rand.nextBoolean();
        if (!will_run){
            return false;
        }

        List<Point> neighbours = my_world.get_neighbours(this.get_position(), false);
        if (neighbours.isEmpty()){
            return false;
        }

        int pos = rand.nextInt(neighbours.size());
        Point new_position = neighbours.get(pos);
        my_world.update_creature_position(this, new_position);
        return true;
    }

    @Override
    public boolean collision(Creature other) {
       boolean run = run_away();
       if (run){
           return true;
       } else {
           return super.collision(other);
       }
    }
}
