package window;

import organisms.Creature;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class MainWindow {
    private JFrame window;
    private DrawPanel drawPanel = new DrawPanel();
    private JPanel infoPanel = create_info_panel();
    private JPanel logPanel = create_log_panel();
    private JLabel current_input;
    private World world;


    private final static Image[] images = {
            new ImageIcon("src/wolf.png").getImage(),
    };

    public MainWindow() {
        window = create_window();
    }

    private JFrame create_window() {
        JFrame window = new JFrame("World");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1920, 1080);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.add(drawPanel, BorderLayout.CENTER);
        window.add(infoPanel, BorderLayout.WEST);
        window.add(logPanel, BorderLayout.EAST);
        return window;
    }

    private JPanel create_info_panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 600));
        panel.setBackground(Color.RED);
        JButton next_turn_button = create_next_turn_button();
        panel.add(next_turn_button);
        current_input = new JLabel("Current input: ");
        panel.add(current_input);
        return panel;
    }

    private JPanel create_log_panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 600));
        panel.setBackground(Color.GREEN);
        return panel;
    }

    private JButton create_next_turn_button() {
        JButton button = new JButton("Next turn");
        button.setFocusable(false);
        button.addActionListener(e -> {
            world.play_turn();
            world.print_creatures();
            System.out.println("Next turn");
            drawPanel.repaint();
        });
        return button;
    }

    public void show() {
        window.setVisible(true);
    }

    public void set_world(World world) {
        this.world = world;
    }

    private void set_current_input(char input) {
        current_input.setText("Current input: " + input);
    }

    private void add_creature(int x, int y) {
        Class<?> creature = (Class<?>) JOptionPane.showInputDialog(window,
                "Pick and item: ", "Input", JOptionPane.QUESTION_MESSAGE,
                null, Creature.organisms, "Item 1");;
        System.out.println("Result: " + creature);
    }

    class KeyHandler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("Key typed: " + e.getKeyChar());

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyChar());
            set_current_input(e.getKeyChar());
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
        private Point get_grid_position(int x, int y) {
            int grid_x = x / 100;
            int grid_y = y / 100;
            return new Point(grid_x, grid_y);
        }

        class MouseHandler extends MouseAdapter {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Point grid_position = get_grid_position(e.getX(), e.getY());
                System.out.println("Clicked on grid position: " + grid_position);
                add_creature(grid_position.x, grid_position.y);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image image = new ImageIcon("src/wolf.png").getImage();
            g.setColor(Color.DARK_GRAY);
            int panel_size = Math.min(window.getWidth() -
                    logPanel.getPreferredSize().width -  infoPanel.getPreferredSize().width, window.getHeight() - 100);
            int image_size = panel_size / world.get_height();

            for (int i = 0; i < world.get_height(); i++) {
                for (int j = 0; j < world.get_width(); j++) {
                    Creature creature = world.get_creature(new Point(i, j));
                    if (creature == null){
                        g.fillRect(i * image_size, j * image_size, image_size, image_size);
                    } else {
                        g.drawImage(image, i * image_size, j * image_size, image_size, image_size, this);
                    }
                    //System.out.println("Drawing image at: " + i + " " + j);
                }
            }
            //g.fillRect(0, 0, 100, 100);
        }
    }
}
