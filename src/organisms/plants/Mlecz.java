package organisms.plants;

import java.awt.*;

public class Mlecz extends Plant{
    public Mlecz() {
        super(0, 0, 0, null);
    }

    public Mlecz(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    @Override
    protected void plant_new(Point position) {
        for (int i = 0; i < 3; i++) {
            super.plant_new(position);
        }
    }
}
