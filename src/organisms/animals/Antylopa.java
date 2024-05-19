package organisms.animals;

import logger.EventLogger;
import organisms.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antylopa extends Animal {

    public Antylopa() {
        super(4, 4, 0, null);
    }

    public Antylopa(int power, int initiative, int age, Point position) {
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
        EventLogger.log("Antylopa ran to " + new_position.toString() + "!");
        return true;
    }

    @Override
    protected Point move_target() {
        List<Point> neighbours = my_world.get_neighbours(this.get_position(), true);

        if (neighbours.isEmpty()){
            return this.get_position();
        }

        List<Point> to_be_added = new ArrayList<Point>();
        for (Point neighbour : neighbours) {
            if (neighbour.x - 1 >= 0 &&
                    my_world.get_creature(new Point(neighbour.x - 1, neighbour.y)) != this &&
                    !neighbours.contains(new Point(neighbour.x - 1, neighbour.y)) &&
                    !to_be_added.contains(new Point(neighbour.x - 1, neighbour.y))) {
                to_be_added.add(new Point(neighbour.x - 1, neighbour.y));
            }

            if (neighbour.x + 1 < my_world.get_width() &&
                    my_world.get_creature(new Point(neighbour.x + 1, neighbour.y)) != this &&
                    !neighbours.contains(new Point(neighbour.x + 1, neighbour.y)) &&
                    !to_be_added.contains(new Point(neighbour.x + 1, neighbour.y))) {
                to_be_added.add(new Point(neighbour.x + 1, neighbour.y));
            }

            if (neighbour.y - 1 >= 0 &&
                    my_world.get_creature(new Point(neighbour.x, neighbour.y - 1)) != this &&
                    !neighbours.contains(new Point(neighbour.x, neighbour.y - 1)) &&
                    !to_be_added.contains(new Point(neighbour.x, neighbour.y - 1))) {
                to_be_added.add(new Point(neighbour.x, neighbour.y - 1));
            }

            if (neighbour.y + 1 < my_world.get_height() &&
                    my_world.get_creature(new Point(neighbour.x, neighbour.y + 1)) != this &&
                    !neighbours.contains(new Point(neighbour.x, neighbour.y + 1)) &&
                    !to_be_added.contains(new Point(neighbour.x, neighbour.y + 1))) {
                to_be_added.add(new Point(neighbour.x, neighbour.y + 1));
            }
        }

        neighbours.addAll(to_be_added);

        for (Point neighbour: neighbours){
            System.out.println(neighbour);
        }

        Random rnd = new Random();
        int pos = rnd.nextInt(neighbours.size());

        return neighbours.get(pos);
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
