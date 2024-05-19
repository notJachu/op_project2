package organisms.animals;
import organisms.Creature;

import java.awt.Point;

public class Czlowiek extends Animal{
    public Czlowiek() {
        super(5, 4, 0, null);
    }

    public Czlowiek(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    @Override
    protected Point move_target(){
        int ability = my_world.get_ability_cooldown();
        if (ability == 0) {
            power += 5;
        }
        if (ability < 0 && ability >= -5) {
            power -= 1;
        }
        if (ability > -10) {
            my_world.set_ability_cooldown(ability - 1);
        }

        Point target = my_world.get_player_input();
        if (target == null) {
            return position;
        }

        System.out.println(target);

        Point new_position = new Point(position.x + target.x, position.y + target.y);

        if (new_position.x < 0 || new_position.x >= my_world.get_width() || new_position.y < 0 || new_position.y >= my_world.get_height()) {
            return position;
        }

        System.out.println("Czlowiek moves to " + target.x + " " + target.y);
        return new_position;
    }


}
