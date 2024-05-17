package organisms.animals;
import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Lis extends Animal{
    public Lis() {
        super(3, 7, 0, null);
    }

    public Lis(int power, int initiative, int age, java.awt.Point position) {
        super(power, initiative, age, position);
    }

    @Override
    protected Point move_target() {
        List<Point> neighbours = my_world.get_neighbours(this.position, true);

        if (neighbours.isEmpty()){
            return this.position;
        }

        for (Point neighbour : neighbours) {
            if (my_world.get_creature(neighbour).get_power() >= this.power) {
                neighbours.remove(neighbour);
            }
        }

        if (neighbours.isEmpty()){
            return this.position;
        }

        Random rnd = new Random();
        int pos = rnd.nextInt(neighbours.size());

        return neighbours.get(pos);
    }
}
