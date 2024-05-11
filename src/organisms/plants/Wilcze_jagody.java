package organisms.plants;

import java.awt.Point;

public class Wilcze_jagody extends Plant{
    public Wilcze_jagody() {
        super(99, 0, 0, null);
    }

    public Wilcze_jagody(Point position) {
        super(99, 0, 0, position);
    }


    public Wilcze_jagody(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }
}
