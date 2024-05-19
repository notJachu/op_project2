package world;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import organisms.Creature;

public class World {
    private final boolean map_type;
    protected int width;
    protected int height;
    private int ability_cooldown;

    private Point player_input = new Point(0, 0);
    Creature[][] world_map;
    List<Creature> creatures;


    //CLASS INIT

    private void generate_world() {
        // Generate world
        world_map = new Creature[height][width];
        creatures = new ArrayList<>();
    }

    public World() {
        this.height = 20;
        this.width = 20;
        this.ability_cooldown = -10;
        this.map_type = false;
        generate_world();
    }

    public World(int height, int width) {
        this.height = height;
        this.width = width;
        this.ability_cooldown = -10;
        this.map_type = false;
        generate_world();
    }

    public void save_world(){
        try {
            FileWriter writer = new FileWriter("src/saves/world.txt");
            writer.write("0\n");
            writer.write(this.width + " " + this.height + " " + this.ability_cooldown + "\n");
            for (Creature creature : creatures) {
                Point position = creature.get_position();
                writer.write(position.x + " " + position.y + " " + creature.get_power() + " " + creature.get_initiative() + " " + creature.get_age() + " " + creature.getClass().getName() + "\n");
            }
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    Class<?> string_to_class(String type){
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void load_world(Scanner reader){
            this.width = reader.nextInt();
        this.height = reader.nextInt();
        this.ability_cooldown = reader.nextInt();

        while (reader.hasNextLine()){
            String line = reader.nextLine();
            if (line.isEmpty()){
                continue;
            }

            String[] parts = line.split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int power = Integer.parseInt(parts[2]);
            int initiative = Integer.parseInt(parts[3]);
            int age = Integer.parseInt(parts[4]);
            String type = parts[5];

            Class<?> creature_class = string_to_class(type);

            Creature creature = Creature.create_creature(creature_class);
            creature.set_position(new Point(x, y));
            creature.set_power(power);
            creature.set_initiative(initiative);
            creature.set_age(age);
            this.add_creature(creature);
        }
    }

    public void load_world(){
        File file = new File("src/saves/world.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int type = reader.nextInt();
        load_world(reader);
        reader.close();
    }

    //GETTERS

    public int get_height(){
        return this.height;
    }

    public int get_width(){
        return this.width;
    }

    public int get_creatures_size(){
        return creatures.size();
    }

    public int get_ability_cooldown(){
        return this.ability_cooldown;
    }

    public void set_ability_cooldown(int ability_cooldown){
        this.ability_cooldown = ability_cooldown;
    }

    public Point get_player_input(){
        return this.player_input;
    }

    public void set_player_input(Point player_input){
        this.player_input = player_input;
    }

    public boolean get_map_type(){
        return this.map_type;
    }


    public Creature get_creature(Point position){
        return world_map[position.x][position.y];
    }

    //LOGIC

    public void remove_creature(Creature creature) {
        // Remove creature from the world
        Point position = creature.get_position();
        world_map[position.x][position.y] = null;
        creatures.remove(creature);
    }

    public void add_creature(Creature creature) {
        // Add creature to the world
        creature.set_world(this);
        Point position = creature.get_position();
        world_map[position.x][position.y] = creature;
        creatures.add(creature);
    }

    public void update_creature_position(Creature creature, Point new_position){
        // Update creature position
        Point old_position = creature.get_position();
        world_map[old_position.x][old_position.y] = null;
        world_map[new_position.x][new_position.y] = creature;
        creature.set_position(new_position);
    }

    public List<Point> get_neighbours(Point position, boolean include_occupied) {
        // Get neighbours of the position

        List<Point> res = new ArrayList<Point>();
        if (position.x - 1 >= 0){
            if (include_occupied || world_map[position.x - 1][position.y] == null){
                res.add(new Point(position.x - 1, position.y));
            }
        }

        if (position.x + 1 < width){
            if (include_occupied || world_map[position.x + 1][position.y] == null){
                res.add(new Point(position.x + 1, position.y));
            }
        }

        if (position.y - 1 >= 0){
            if (include_occupied || world_map[position.x][position.y - 1] == null){
                res.add(new Point(position.x, position.y - 1));
            }
        }

        if (position.y + 1 < height){
            if (include_occupied || world_map[position.x][position.y + 1] == null){
                res.add(new Point(position.x, position.y + 1));
            }
        }

        return res;
    }

    public void print_creatures() {
        // Print all creatures
        for (Creature creature : creatures) {
            System.out.println(creature);
        }
    }


    public void play_turn() {
        // Play one turn

        // sort creatures by initiative
        creatures.sort(new Creature.compare_creatures());

        int creatures_size = creatures.size();

        for (int i = 0; i < creatures_size; i++){
            Creature creature = creatures.get(i);
            creature.action();
        }

        // loop twice so all creatures age at once
        for (int i = 0; i < creatures_size; i++){
            Creature creature = creatures.get(i);
            creature.increment_age(1);
        }

    }

}
