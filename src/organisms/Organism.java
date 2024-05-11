package organisms;

import organisms.animals.Antylopa;
import organisms.animals.Wolf;
import organisms.plants.Trawa;

import java.awt.*;

public class Organism {
    // put here all types
    public final static Class<?>[] organisms = {
            Wolf.class,
            Antylopa.class,
            Trawa.class
    };

    private int power;
    private int initiative;
    private int age;
    private Point position;

}
