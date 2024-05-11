package world;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import organisms.Creature;

public class World {
    private boolean map_type;
    private int width;
    private int height;
    private int ability_cooldown;
    Creature[][] world_map;
    List<Creature> creatures;


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

    }

    public void load_world(){

    }

    public void set_map_type(boolean map_type) {
        this.map_type = map_type;
    }

    public void remove_creature(Creature creature) {
        // Remove creature from the world
        Point position = creature.get_position();
        world_map[position.x][position.y] = null;
        creatures.remove(creature);
    }

    public void add_creature(Creature creature) {
        // Add creature to the world
        Point position = creature.get_position();
        world_map[position.x][position.y] = creature;
        creatures.add(creature);
    }

    private List<Point> get_hex_neighbours(Point position, boolean include_ocupied){

        return null;
    }

    private List<Point> get_square_neighbours(Point position, boolean include_occupied){
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

    public List<Point> get_neighbours(Point position, boolean include_ocupied) {
        // Get neighbours of the position

        List<Point> res;

        if (map_type){
            res = get_hex_neighbours(position, include_ocupied);
        } else {
            res = get_square_neighbours(position, include_ocupied);
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
    }

}
