package window;

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

    private final static Image[] images = {
            new ImageIcon("src/wolf.png").getImage(),
    };

    public MainWindow() {
        window = create_window();
    }

    private JFrame create_window() {
        JFrame window = new JFrame("World");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.add(drawPanel, BorderLayout.CENTER);
        window.add(infoPanel, BorderLayout.WEST);
        window.add(logPanel, BorderLayout.EAST);
        window.addKeyListener(new KeyHandler());
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
            System.out.println("Next turn");
        });
        return button;
    }

    public void show() {
        window.setVisible(true);
    }

    private void set_current_input(char input) {
        current_input.setText("Current input: " + input);
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
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image image = new ImageIcon("src/wolf.png").getImage();
            g.setColor(Color.RED);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    g.drawImage(image, i * 100, j * 100, 100, 100, this);
                }
            }
            //g.fillRect(0, 0, 100, 100);
        }
    }
}
