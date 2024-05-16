package world;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HexWorld extends World{

    private final int[][] directions = {
        {0, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}
    };

    public HexWorld() {
        super();
    }

    public HexWorld(int height, int width) {
        super(height, width);
    }

    @Override
    public List<Point> get_neighbours(Point position, boolean include_occupied) {
        // get neighbours

        List<Point> res = new ArrayList<Point>();

        for (int[] dir : directions) {
            Point new_point = new Point(position.x + dir[0], position.y + dir[1]);
            if (new_point.x >= 0 && new_point.x < this.width && new_point.y >= 0 && new_point.y < this.height) {
                if (include_occupied || this.get_creature(new_point) == null) {
                    res.add(new_point);
                }
            }
        }


        return res;
    }

}
