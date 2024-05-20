package organisms;

import organisms.animals.*;
import organisms.plants.*;
import world.World;

import java.awt.*;
import java.util.Comparator;

public class Creature {
    // put here all types
    public final static Class<?>[] organisms = {
            Wolf.class,
            Antylopa.class,
            Lis.class,
            Owca.class,
            Tutel.class,
            Trawa.class,
            Mlecz.class,
            Guarana.class,
            Wilcze_jagody.class,
            BarszczSosnowskiego.class,
            Czlowiek.class
    };

    protected int power;
    protected int initiative;
    protected int age;
    protected Point position;
    protected World my_world;

    public Creature() {
        this.power = 0;
        this.initiative = 0;
        this.age = 0;
        this.position = new Point(-1, -1);
    }

    public Creature(int power, int initiative, int age, Point position) {
        this.power = power;
        this.initiative = initiative;
        this.age = age;
        this.position = position;
    }

    public static class compare_creatures implements Comparator<Creature> {

        public int compare(Creature a, Creature b){
            return b.initiative - a.initiative;
        }
    }

    public void action() {
        // do something
    }

    public boolean collision(Creature other) {
        return false;
    }

    public void increment_age(int amount){
        this.age += amount;
    }

    public void kill() {
        // kill the organism
        my_world.remove_creature(this);
    }

    public void set_world(World world) {
        this.my_world = world;
    }

    public void set_position(Point position) {
        this.position = position;
    }

    public void set_power(int power) {
        this.power = power;
    }

    public void set_initiative(int initiative) {
        this.initiative = initiative;
    }

    public void set_age(int age) {
        this.age = age;
    }

    public static Creature create_creature(Class<?> organism) {
        Creature creature = null;
        for (Class<?> org : organisms) {
            if (org == organism) {
                try {
                    System.out.println(organism.getConstructors()[0]);
                    System.out.println(organism.getConstructors()[1]);
                    creature = (Creature) organism.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return creature;
    }

    public Point get_position() {
        return this.position;
    }

    public int get_power() {
        return this.power;
    }

    public int get_initiative() {
        return this.initiative;
    }

    public int get_age() {
        return this.age;
    }

    public World get_world() {
        return this.my_world;
    }



    @Override
    public String toString() {
        return "Creature{" +
                "power=" + power +
                ", initiative=" + initiative +
                ", age=" + age +
                ", position=" + position +
                ", my_world=" + my_world +
                '}';
    }

}
