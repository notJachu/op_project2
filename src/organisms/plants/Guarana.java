package organisms.plants;
import logger.EventLogger;
import organisms.Creature;

public class Guarana extends Plant{

    public Guarana() {
        super(0, 0, 0, null);
    }

    public Guarana(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

   @Override
    public boolean collision(Creature other) {
        other.set_power(other.get_power() + 3);
       EventLogger.log("Guarana gave " + other.getClass().getSimpleName() + " 3 power!");
        my_world.remove_creature(this);
        return true;
    }
}
