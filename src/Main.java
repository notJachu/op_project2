import logger.EventLogger;
import organisms.Creature;
import organisms.plants.Plant;
import organisms.plants.Trawa;
import window.MainWindow;
import world.World;
import world.HexWorld;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        Creature creature = new Creature(1, 1, 1, new Point(1, 2));
//        Plant plant = new Plant(0, 0, 0, new Point(1, 1));
        Trawa trawa = new Trawa(0, 0, 0, new Point(0, 1));
        World world = new World();
//        world.add_creature(creature);
//        world.add_creature(plant);
        world.add_creature(trawa);
        //world.print_creatures();
//        world.remove_creature(creature);
       // world.play_turn();
       // world.print_creatures();

      //world.save_world();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main_window = new MainWindow();
                main_window.set_world(world);
                main_window.show();
                EventLogger.set_log_panel(main_window.get_log_panel());
                EventLogger.create_log_area();
                //EventLogger.log("Hello World!");
            }
        });
    }

    private void loadWorld() {
        try {
            File read_file = new File("src/saves/world.txt");
            if (!read_file.exists()) {
                System.out.println("File does not exist");
                return;
            }
            Scanner reader = new Scanner(read_file);
            int type = reader.nextInt();
            if (type == 0) {
                World world = new World();
                world.load_world(reader);
            } else {
                HexWorld world = new HexWorld();
                world.load_world(reader);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}