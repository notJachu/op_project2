package logger;

import javax.swing.*;

public class EventLogger {
    private static JPanel log_panel;
    private static JLabel log_area;
    private static String log_text = "";

   public static void set_log_panel(JPanel panel) {
        log_panel = panel;
    }

    public static void set_log_area(JLabel area) {
        log_area = area;
        if (log_panel != null) {
            log_panel.add(log_area);
        }
    }

    public static void create_log_area() {
        log_area = new JLabel();
        log_area.setVerticalAlignment(SwingConstants.TOP);
        log_area.setHorizontalAlignment(SwingConstants.LEFT);
        log_area.setText("<html>" + log_text + "</html>");
        if (log_panel != null) {
            log_panel.add(log_area);
        }
    }
    public static void log(String message) {
        log_text += message + "<br>";
    }

    public static void update() {
        log_area.setText("<html>" + log_text + "</html>");
        log_text = "";
    }
}
