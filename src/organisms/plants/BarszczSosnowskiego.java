package organisms.plants;
import logger.EventLogger;
import organisms.Creature;
import organisms.animals.CyberOwca;
import java.util.List;

import java.awt.*;

public class BarszczSosnowskiego extends Plant{
    public BarszczSosnowskiego() {
        super(10, 0, 0, null);
    }

    public BarszczSosnowskiego(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    @Override
    public void action() {
        super.action();
        List<Point> neighbours = my_world.get_neighbours(this.get_position(), true);
        for (Point neighbour : neighbours) {
            Creature creature = my_world.get_creature(neighbour);
            if (creature != null && creature.getClass() != CyberOwca.class) {
                EventLogger.log("Barszcz Sosnowskiego at " + this.get_position() + " killed " + creature.getClass().getSimpleName() + "!");
                creature.kill();
            }
        }
    }
    @Override
    public boolean collision(Creature other) {
        if (other.getClass() != CyberOwca.class) {
            other.kill();
            return false;
        }
        my_world.remove_creature(this);
        return true;
    }
}
