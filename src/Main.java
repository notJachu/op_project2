import organisms.Creature;
import organisms.plants.Plant;
import world.World;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Creature creature = new Creature(1, 1, 1, new Point(1, 2));
        Plant plant = new Plant(0, 0, 0, new Point(1, 1));
        World world = new World();
        world.add_creature(creature);
        world.add_creature(plant);
        world.print_creatures();
        world.remove_creature(creature);
        world.print_creatures();

    }
}