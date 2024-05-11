public class World {
    private boolean map_type;
    private int width;
    private int height;
    private int ability_cooldown;

    public World() {
        this.height = 20;
        this.width = 20;
        this.ability_cooldown = -10;
        this.map_type = false;
    }

    public World(int height, int width) {
        this.height = height;
        this.width = width;
        this.ability_cooldown = -10;
        this.map_type = false;
    }
}
