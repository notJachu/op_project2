import organisms.Creature;
import organisms.plants.Plant;
import organisms.plants.Trawa;
import window.MainWindow;
import world.World;
import world.HexWorld;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
//        Creature creature = new Creature(1, 1, 1, new Point(1, 2));
//        Plant plant = new Plant(0, 0, 0, new Point(1, 1));
        Trawa trawa = new Trawa(0, 0, 0, new Point(0, 1));
        World world = new World();
//        world.add_creature(creature);
//        world.add_creature(plant);
        //world.add_creature(trawa);
        //world.print_creatures();
//        world.remove_creature(creature);
       // world.play_turn();
       // world.print_creatures();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main_window = new MainWindow();
                main_window.set_world(world);
                main_window.show();
            }
        });
    }
}