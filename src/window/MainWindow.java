package window;

import organisms.Creature;
import organisms.animals.*;
import organisms.plants.*;
import world.HexWorld;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.Map;

public class MainWindow {
    private JFrame window;
    private DrawPanel drawPanel = new DrawPanel();
    private DrawHexPanel drawHexPanel = new DrawHexPanel();
    private JPanel infoPanel = create_info_panel();
    private JPanel logPanel = create_log_panel();
    private JLabel current_input;
    private static World world;
    private static boolean hex_mode = false;

    private final static int WINDOW_WIDTH = 1920;
    private final static int WINDOW_HEIGHT = 1080;
    private final static int SIDE_PANEL_WIDTH = 200;
    private final static int LOG_PANEL_WIDTH = 200;
    private final static int DRAW_PANEL_WIDTH = WINDOW_WIDTH - SIDE_PANEL_WIDTH - LOG_PANEL_WIDTH;
    private final static int DRAW_PANEL_HEIGHT = WINDOW_HEIGHT;

    private static int image_size;



    private final static Map<Class<?>, Image> images = Map.ofEntries(
            Map.entry(Wolf.class, new ImageIcon("src/wolf.png").getImage()),
            Map.entry(Antylopa.class, new ImageIcon("src/antylopa.png").getImage()),
            Map.entry(Lis.class, new ImageIcon("src/lis.png").getImage()),
            Map.entry(Owca.class, new ImageIcon("src/owca.png").getImage()),
            Map.entry(Tutel.class, new ImageIcon("src/tutel.png").getImage()),
            Map.entry(Trawa.class, new ImageIcon("src/trawa.png").getImage()),
            Map.entry(Mlecz.class, new ImageIcon("src/mlecz.png").getImage()),
            Map.entry(Guarana.class, new ImageIcon("src/guarana.png").getImage()),
            Map.entry(Wilcze_jagody.class, new ImageIcon("src/wilcze_jagody.png").getImage()),
            Map.entry(Czlowiek.class, new ImageIcon("src/human.png").getImage()),
            Map.entry(BarszczSosnowskiego.class, new ImageIcon("src/barszcz_sosnowskiego.png").getImage())
    );

    public MainWindow() {
        window = create_window();
        image_size = DRAW_PANEL_WIDTH / 20;
        HexField.setHeight(image_size);
    }

    public MainWindow(World world) {
        this();
        this.world = world;
        int panel_size = Math.min(window.getWidth() -
                logPanel.getPreferredSize().width -  infoPanel.getPreferredSize().width, WINDOW_HEIGHT - 100);
        image_size = panel_size / world.get_height();
        HexField.setHeight(image_size);

    }


    private JFrame create_window() {
        JFrame window = new JFrame("Jan Stąsiek 197741");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        if (hex_mode) {
            window.add(drawHexPanel, BorderLayout.CENTER);
        } else {
            window.add(drawPanel, BorderLayout.CENTER);
        }

        window.add(infoPanel, BorderLayout.WEST);
        window.add(logPanel, BorderLayout.EAST);
        window.addKeyListener(new KeyHandler());
        return window;
    }

    private JPanel create_info_panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, WINDOW_HEIGHT));
        panel.setBackground(Color.RED);
        JButton next_turn_button = create_next_turn_button();
        panel.add(next_turn_button);
        JButton save_button = create_save_button();
        JButton load_button = create_load_button();
        JButton resize_button = create_resize_button();
        JButton change_world_button = create_change_world_button();
        panel.add(save_button);
        panel.add(load_button);
        panel.add(resize_button);
        panel.add(change_world_button);
        current_input = new JLabel("Current input: ");
        panel.add(current_input);
        return panel;
    }

    private JPanel create_log_panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(LOG_PANEL_WIDTH, WINDOW_HEIGHT));
        panel.setBackground(Color.GREEN);
        panel.setFocusable(false);
        panel.setEnabled(false);
        return panel;
    }

    private JButton create_next_turn_button() {
        JButton button = new JButton("Next turn");
        button.setFocusable(false);
        button.addActionListener(e -> {
            world.play_turn();
            world.print_creatures();
            System.out.println(world.get_creatures_size());
            System.out.println("Next turn");
            drawHexPanel.revalidate();
            drawPanel.revalidate();
            drawHexPanel.repaint();
            drawPanel.repaint();
        });
        return button;
    }

    private JButton create_save_button() {
        JButton button = new JButton("Save");
        button.setFocusable(false);
        button.addActionListener(e -> {
            world.save_world();
            JOptionPane.showMessageDialog(window, "World was saved",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        return button;
    }

    private JButton create_load_button() {
        JButton button = new JButton("Load");
        button.setFocusable(false);
        button.addActionListener(e -> {
            world = new World();
            world.load_world();

            JOptionPane.showMessageDialog(window, "World was loaded",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            drawPanel.repaint();
        });
        return button;
    }
    public void show() {
        window.setVisible(true);
    }

    public void set_world(World world) {
        this.world = world;
        int panel_size = Math.min(window.getWidth() -
                logPanel.getPreferredSize().width -  infoPanel.getPreferredSize().width, window.getHeight() - 100);
        image_size = panel_size / world.get_height();
        HexField.setHeight(image_size);
    }

    private JButton create_resize_button() {
        JButton button = new JButton("Resize");
        button.setFocusable(false);
        button.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("width:"));
            JTextField textField1 = new JTextField();
            panel.add(textField1);
            panel.add(new JLabel("height:"));
            JTextField textField2 = new JTextField();
            panel.add(textField2);

            int result = JOptionPane.showConfirmDialog(null, panel, "Enter two numbers", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int new_width = Integer.parseInt(textField1.getText());
                    int new_height = Integer.parseInt(textField2.getText());

                    world = new World(new_height, new_width);
                    int panel_size = Math.min(window.getWidth() -
                            logPanel.getPreferredSize().width -  infoPanel.getPreferredSize().width, WINDOW_HEIGHT - 100);
                    image_size = panel_size / world.get_height();
                    HexField.setHeight(image_size);
                    if (hex_mode) {
                        drawHexPanel.repaint();
                    } else {
                        drawPanel.repaint();
                    }
                } catch (NumberFormatException ev) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Cancelled");
            }
        });
        return button;
    }

    private JButton create_change_world_button() {
        JButton button = new JButton("Change world");
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (hex_mode) {
                window.remove(drawHexPanel);
                window.add(drawPanel, BorderLayout.CENTER);
               hex_mode = false;
               drawPanel.revalidate();
                drawPanel.repaint();
               world = new World();
            } else {
                window.remove(drawPanel);
                window.add(drawHexPanel, BorderLayout.CENTER);
                drawHexPanel.revalidate();
                drawHexPanel.repaint();
                hex_mode = true;
                world = new HexWorld();
            }
        });
        return button;
    }

    public JPanel get_log_panel() {
        return logPanel;
    }

    private void set_current_input(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                world.set_player_input(new Point(0, -1));
                current_input.setText("Current input: ^");
                break;
            case KeyEvent.VK_DOWN:
                world.set_player_input(new Point(0, 1));
                current_input.setText("Current input: v");
                break;
            case KeyEvent.VK_LEFT:
                world.set_player_input(new Point(-1, 0));
                current_input.setText("Current input: <");
                break;
            case KeyEvent.VK_RIGHT:
                world.set_player_input(new Point(1, 0));
                current_input.setText("Current input: >");
                break;
            case KeyEvent.VK_P:
                int ability = world.get_ability_cooldown();
                if (ability == -10) {
                    world.set_ability_cooldown(0);
                    JOptionPane.showMessageDialog(window, "Ability was used",
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window, "Can't use ability now",
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            default:
                return;
        }

    }

    private void add_creature(int x, int y) {
        Class<?> creature = (Class<?>) JOptionPane.showInputDialog(window,
                "Pick and item: ", "Input", JOptionPane.QUESTION_MESSAGE,
                null, Creature.organisms, "Item 1");;
        System.out.println("Result: " + creature);
        try {
            Creature new_creature = Creature.create_creature(creature);
            new_creature.set_position(new Point(x, y));
            world.add_creature(new_creature);
            drawPanel.repaint();
            drawHexPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawPanel.repaint();
    }

    class KeyHandler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("Key typed: " + e.getKeyChar());

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyChar());
            set_current_input(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("Key released: " + e.getKeyChar());
        }
    }


    class DrawPanel extends JPanel {

        public DrawPanel() {
            addMouseListener(new MouseHandler());
        }
        protected Point get_grid_position(int x, int y) {
            int grid_x = x / image_size;
            int grid_y = y / image_size;
            return new Point(grid_x, grid_y);
        }

        class MouseHandler extends MouseAdapter {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Point grid_position = get_grid_position(e.getX(), e.getY());
                if (grid_position.x >= world.get_width() || grid_position.y >= world.get_height()){
                    return;
                }
                Creature creature = world.get_creature(grid_position);
                if (creature != null){
                    System.out.println("Clicked on creature: " + creature);
                    return;
                }
                System.out.println("Clicked on grid position: " + grid_position);
                add_creature(grid_position.x, grid_position.y);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.DARK_GRAY);


            for (int i = 0; i < world.get_height(); i++) {
                for (int j = 0; j < world.get_width(); j++) {
                    Creature creature = world.get_creature(new Point(i, j));
                    if (creature == null){
                        g.fillRect(i * image_size, j * image_size, image_size, image_size);
                    } else {
                        Image image = images.get(creature.getClass());
                        g.drawImage(image, i * image_size, j * image_size, image_size, image_size, this);
                    }
                    //System.out.println("Drawing image at: " + i + " " + j);
                }
            }
            //g.fillRect(0, 0, 100, 100);
        }
    }

    class DrawHexPanel extends JPanel{
        public DrawHexPanel() {
            HexField.setHeight(image_size);
            addMouseListener(new MouseHandler());
        }

        class MouseHandler extends MouseAdapter {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Point grid_position = HexField.point_to_grid(e.getX(), e.getY());
                System.out.println("Clicked on grid position: " + grid_position);
                if (grid_position.x >= world.get_width() || grid_position.y >= world.get_height()){
                    return;
                }
                Creature creature = world.get_creature(grid_position);
                if (creature != null){
                    System.out.println("Clicked on creature: " + creature);
                    return;
                }
                System.out.println("Clicked on grid position: " + grid_position);
                add_creature(grid_position.x, grid_position.y);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);

            for (int i = 0; i < world.get_height(); i++) {
                for (int j = 0; j < world.get_width(); j++) {
                    HexField.draw(g, i, j);
                    //System.out.println("Drawing image at: " + i + " " + j);
                }
            }

        }

    }

    class HexField {

        private static int h = 0;
        private static int r = 0;
        private static int s = 0;
        private static int t = 0;

        public static void setHeight(int height) {
            h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
            r = h/2;			// r = radius of inscribed circle
            s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
            t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
        }
        public static Polygon create_hex(int x0, int y0){
            int x = x0 + 20;
            int y = y0 + 20;

            int[] cx,cy;

            cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};

            cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
            return new Polygon(cx,cy,6);
        }

        public static Point point_to_grid(int ex, int ey){
            Point res = new Point(0, -0);
            ex -= 20;
            ey -= 20;

            int x = (int) (ex/ (s+t));
            int y = (int) ((ey - (x%2)*r)/h);

            int dx = ex- x*(s+t);
            int dy = ey - y*h;

            if (ey - (x%2)*r < 0) return res;

            if (x%2==0) {
                if (dy > r) {
                    if (dx * r /t < dy - r) {
                        x--;
                    }
                }
                if (dy < r) {
                    if ((t - dx)*r/t > dy ) {
                        x--;
                        y--;
                    }
                }
            } else {
                if (dy > h) {
                    if (dx * r/t < dy - h) {
                        x--;
                        y++;
                    }
                }
                if (dy < h) {
                    if ((t - dx)*r/t > dy - r) {
                        x--;
                    }
                }
            }
            res.x = x;
            res.y = y;
            return res;
        }
        public static void draw(Graphics g, int i, int j) {
            int x = i * (s+t);
            int y = j * h + (i%2) * h/2;
            Polygon hex = create_hex(x,y);
            //g.setColor(Color.GREEN);
            g.setClip(hex);

            Creature creature = world.get_creature(new Point(i, j));
            if (creature != null){
                Image image = images.get(creature.getClass());
                g.drawImage(image, x, y, null);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillPolygon(hex);
            }
            //g.drawImage(images.get(Trawa.class), x, y, null);
            //g.fillPolygon(hex);
            g.setColor(Color.BLACK);
            g.drawPolygon(hex);
        }
    }
}
