package world;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import organisms.Creature;

public class World {
    private final boolean map_type;
    protected final int width;
    protected final int height;
    private int ability_cooldown;
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

    }

    public void load_world(){

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
