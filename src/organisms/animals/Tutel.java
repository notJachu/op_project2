package organisms.animals;

import logger.EventLogger;
import organisms.Creature;

import java.awt.*;
import java.util.Random;

public class Tutel extends Animal{
    public Tutel() {
        super(2, 1, 0, null);
    }

    public Tutel(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    @Override
    protected Point move_target() {
        double random = Math.random();
        if (random > 0.25){
            return this.position;
        }
        else return super.move_target();
    }

    @Override
    public boolean collision(Creature other) {
    if (other.get_power() < 5){
        EventLogger.log("Tutel defended itself from " + other.toString());
            return false;
        }
        else {
            return super.collision(other);
        }
    }
}
