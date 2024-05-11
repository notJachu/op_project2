package organisms.plants;

import organisms.Creature;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.List;

public class Plant extends Creature {
        public Plant() {
            super(0, 0, 0, null);
        }

        public Plant(int power, int initiative, int age, java.awt.Point position) {
            super(power, initiative, age, position);
        }

        private void plant_new(Point position) {
            Random rnd = new Random();

            int will_plant = rnd.nextInt(100);
            if (will_plant > 25){
                return;
            }

            List<Point> neighbours = my_world.get_neighbours(position, false);

            Creature creature = null;
            for (Class<?> organism : organisms) {
               if (organism == this.getClass()){
                     creature = create_creature(organism);
                        break;
               }
            }

            if (creature == null){
                return;
            }

            if (neighbours.isEmpty()){
                return;
            }

            int pos = rnd.nextInt(neighbours.size());
            creature.set_position(neighbours.get(pos));
            my_world.add_creature(creature);


        }

}
