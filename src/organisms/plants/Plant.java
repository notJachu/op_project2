package organisms.plants;

import organisms.Creature;

import java.awt.*;
import java.util.Random;
import java.util.List;

public class Plant extends Creature {
        public Plant() {
            super(0, 0, 0, null);
        }

        public Plant(int power, int initiative, int age, java.awt.Point position) {
            super(power, initiative, age, position);
        }

        protected void plant_new(Point position) {
            Random rnd = new Random();

            int will_plant = rnd.nextInt(100);
            if (will_plant < 25){
                return;
            }

            List<Point> neighbours = my_world.get_neighbours(position, false);

            Creature creature = create_creature(this.getClass());

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

        @Override
        public void action() {
            plant_new(this.position);
        }

        @Override
        public boolean collision(Creature other) {
            return true;
        }

    @Override
    public String toString() {
        return "Plant{" +
                "type=" + this.getClass().getSimpleName() +
                ", power=" + power +
                ", initiative=" + initiative +
                ", age=" + age +
                ", position=" + position +
                ", my_world=" + my_world +
                '}';
    }

}
