import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class Main {
    public static void main(String[] args) {
        BasicLookAndFeel darcula = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(darcula);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new RoomPlanner();
    }
}
